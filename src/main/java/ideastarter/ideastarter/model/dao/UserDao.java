package ideastarter.ideastarter.model.dao;

import ideastarter.ideastarter.model.dto.ShowUserDto;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.util.exception.ImageMissingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ShowUserDto getUserById(long id) throws SQLException {
        ShowUserDto showUser = new ShowUserDto();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id,first_name,last_name,email,image_url FROM users WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                showUser.setId(rs.getLong(1));
                showUser.setFirstName(rs.getString(2));
                showUser.setLastName(rs.getString(3));
                showUser.setEmail(rs.getString(4));
                showUser.setImageUrl(rs.getString(5));
            }
        }
        return showUser;
    }

    public void addImageUrl(String dir, String imageUrl, long userId) throws SQLException {
        Connection connection = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("SELECT image_url FROM users WHERE id = ?");
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                File file = new File(dir + rs.getString(1));
                file.delete();
            }
            PreparedStatement putImage = connection.prepareStatement("UPDATE users SET image_url = ? WHERE id = ?");
            putImage.setString(1, imageUrl);
            putImage.setLong(2, userId);
            putImage.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public String getImageUrl(long userId) throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT image_url FROM users WHERE id = ?");
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        }
    }

    public void deleteImage(String dir, User user) throws ImageMissingException, SQLException {
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement("UPDATE users SET image_url = NULL WHERE id = ?");
                ps.setLong(1, user.getId());
                ps.executeUpdate();
                File file = new File(dir + user.getImageUrl());
                if (!file.delete()) {
                    throw new ImageMissingException();
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}
