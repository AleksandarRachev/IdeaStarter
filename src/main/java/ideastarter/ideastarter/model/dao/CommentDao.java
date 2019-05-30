package ideastarter.ideastarter.model.dao;

import ideastarter.ideastarter.model.dto.ShowCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDao {

    @Autowired
    private JdbcTemplate template;
    @Autowired
    private UserDao userDao;

    public List<ShowCommentDto> getAllCommentsForPost(long id) throws SQLException {
        List<ShowCommentDto> comments = new ArrayList<>();
        try (Connection connection = template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id,comment,user_id,post_id FROM comments WHERE post_id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShowCommentDto comment = new ShowCommentDto();
                comment.setId(rs.getLong(1));
                comment.setComment(rs.getString(2));
                comment.setUser(userDao.getUserById(rs.getLong(3)));
                comment.setPostId(rs.getLong(4));
                comments.add(comment);
            }
        }
        return comments;
    }

    public void putCommentOnPost(String comment, long postId, long userId) throws SQLException {
        try (Connection connection = template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO comments (comment,post_id,user_id) VALUES (?,?,?);");
            ps.setString(1, comment);
            ps.setLong(2, postId);
            ps.setLong(3, userId);
            ps.execute();
        }
    }

    public void deleteCommentsByPostId(long postId) throws SQLException {
        try (Connection connection = template.getDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE comments FROM comments WHERE post_id = ?");
            ps.setLong(1, postId);
            ps.execute();
        }
    }
}
