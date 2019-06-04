package ideastarter.ideastarter.model.dao;

import ideastarter.ideastarter.model.dto.CategoryDto;
import ideastarter.ideastarter.model.dto.ShowPostNoUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostDao {

    @Autowired
    private JdbcTemplate template;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentDao commentDao;

    public List<ShowPostNoUserDto> getPostsForUser(long userId) throws SQLException {
        List<ShowPostNoUserDto> posts = new ArrayList<>();
        try (Connection connection = this.template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id,title,description,start_date,end_date,donates,user_id,image_url FROM posts WHERE user_id = ? ORDER BY donates DESC");
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShowPostNoUserDto post = new ShowPostNoUserDto();
                post.setId(rs.getLong(1));
                post.setTitle(rs.getString(2));
                post.setDescription(rs.getString(3));
                post.setStartDate(rs.getDate(4));
                post.setEndDate(rs.getDate(5));
                post.setDonates(rs.getDouble(6));
                post.setUser(userDao.getUserById(rs.getLong(7)));
                post.setImageUrl(rs.getString(8));
                posts.add(post);
            }
        }
        return posts;
    }

    public List<ShowPostNoUserDto> getTop5Posts() throws SQLException {
        List<ShowPostNoUserDto> posts = new ArrayList<>();
        try (Connection connection = this.template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id,title,description,start_date,end_date," +
                    "donates,user_id,image_url FROM posts ORDER BY donates DESC LIMIT 5");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShowPostNoUserDto post = new ShowPostNoUserDto();
                post.setId(rs.getLong(1));
                post.setTitle(rs.getString(2));
                post.setDescription(rs.getString(3));
                post.setStartDate(rs.getDate(4));
                post.setEndDate(rs.getDate(5));
                post.setDonates(rs.getDouble(6));
                post.setUser(userDao.getUserById(rs.getLong(7)));
                post.setImageUrl(rs.getString(8));
                posts.add(post);
            }
        }
        return posts;
    }

    public List<ShowPostNoUserDto> getAllPosts() throws SQLException {
        List<ShowPostNoUserDto> posts = new ArrayList<>();
        try (Connection connection = this.template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id,title,description,start_date,end_date," +
                    "donates,user_id,image_url FROM posts ORDER BY donates DESC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShowPostNoUserDto post = new ShowPostNoUserDto();
                post.setId(rs.getLong(1));
                post.setTitle(rs.getString(2));
                post.setDescription(rs.getString(3));
                post.setStartDate(rs.getDate(4));
                post.setEndDate(rs.getDate(5));
                post.setDonates(rs.getDouble(6));
                post.setUser(userDao.getUserById(rs.getLong(7)));
                post.setImageUrl(rs.getString(8));
                posts.add(post);
            }
        }
        return posts;
    }

    public List<ShowPostNoUserDto> getPostsPerCategory(long categoryId) throws SQLException {
        List<ShowPostNoUserDto> posts = new ArrayList<>();
        try (Connection connection = this.template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id,title,description,start_date,end_date,donates,user_id,image_url" +
                    " FROM posts WHERE category_id = ?");
            ps.setLong(1,categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShowPostNoUserDto post = new ShowPostNoUserDto();
                post.setId(rs.getLong(1));
                post.setTitle(rs.getString(2));
                post.setDescription(rs.getString(3));
                post.setStartDate(rs.getDate(4));
                post.setEndDate(rs.getDate(5));
                post.setDonates(rs.getDouble(6));
                post.setUser(userDao.getUserById(rs.getLong(7)));
                post.setImageUrl(rs.getString(8));
                posts.add(post);
            }
        }
        return posts;
    }

    public List<CategoryDto> getCategories() throws SQLException {
        List<CategoryDto> categories = new ArrayList<>();
        try (Connection connection = this.template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id,name FROM categories");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CategoryDto dto = new CategoryDto();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                categories.add(dto);
            }
        }
        return categories;
    }

    public int countPostsByTitle(String title) throws SQLException {
        try (Connection connection = template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE title LIKE ?");
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        }
    }

    public ShowPostNoUserDto getPostById(long postId) throws SQLException {
        try (Connection connection = template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id,title,description,start_date,end_date,donates FROM posts WHERE id = ?");
            ps.setLong(1, postId);
            ResultSet rs = ps.executeQuery();
            ShowPostNoUserDto post = new ShowPostNoUserDto();
            if (rs.next()) {
                post.setId(rs.getLong(1));
                post.setTitle(rs.getString(2));
                post.setDescription(rs.getString(3));
                post.setStartDate(rs.getDate(4));
                post.setEndDate(rs.getDate(5));
                post.setDonates(rs.getDouble(6));
            }
            return post;
        }
    }

    public void takeDonation(long postId, double donation) throws SQLException {
        try (Connection connection = template.getDataSource().getConnection()) {
            ShowPostNoUserDto post = getPostById(postId);
            PreparedStatement ps = connection.prepareStatement("UPDATE posts SET donates = ? WHERE id = ?");
            ps.setDouble(1,post.getDonates()+donation);
            ps.setLong(2, postId);
            ps.executeUpdate();
        }
    }

    public void addImageUrl(String dir, String imageUrl, long postId) throws SQLException {
        Connection connection = null;
        try {
            connection = template.getDataSource().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("SELECT image_url FROM posts WHERE id = ?");
            ps.setLong(1, postId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                File file = new File(dir + rs.getString(1));
                file.delete();
            }
            PreparedStatement putImage = connection.prepareStatement("UPDATE posts SET image_url = ? WHERE id = ?");
            putImage.setString(1, imageUrl);
            putImage.setLong(2, postId);
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

    public int getTotalPosts() throws SQLException {
        try (Connection connection = template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS posts FROM posts");
            ResultSet rs = ps.executeQuery();
            int count = 0;
            if(rs.next()){
                count = rs.getInt(1);
            }
            return count;
        }
    }

}