package ideastarter.ideastarter.model.dao;

import ideastarter.ideastarter.model.dto.CategoryDto;
import ideastarter.ideastarter.model.dto.ShowPostDto;
import ideastarter.ideastarter.model.dto.ShowPostNoUserDto;
import ideastarter.ideastarter.model.pojo.Category;
import ideastarter.ideastarter.model.pojo.Post;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.repository.UserRepository;
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
public class PostDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDao dao;

    public List<ShowPostDto> getPostsFromUser(User user) throws SQLException {
        List<ShowPostDto> posts = new ArrayList<>();
        try(Connection connection = this.jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT id,title,description,start_date,end_date FROM posts WHERE user_id = ?");
            ps.setLong(1,user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                ShowPostDto post = new ShowPostDto();
                post.setId(rs.getLong(1));
                post.setTitle(rs.getString(2));
                post.setDescription(rs.getString(3));
                post.setStartDate(rs.getDate(4));
                post.setEndDate(rs.getDate(5));
                post.setUser(dao.getUserById(user.getId()));
                posts.add(post);
            }
        }
        return posts;
    }

    public List<ShowPostNoUserDto> getAllPosts() throws SQLException {
        List<ShowPostNoUserDto> posts = new ArrayList<>();
        try(Connection connection = this.jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT id,title,description,start_date,end_date,donates,user_id FROM posts");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                ShowPostNoUserDto post = new ShowPostNoUserDto();
                post.setId(rs.getLong(1));
                post.setTitle(rs.getString(2));
                post.setDescription(rs.getString(3));
                post.setStartDate(rs.getDate(4));
                post.setEndDate(rs.getDate(5));
                post.setDonates(rs.getDouble(6));
                post.setUser(dao.getUserById(rs.getLong(7)));
                posts.add(post);
            }
        }
        return posts;
    }

    public List<CategoryDto> getCategories() throws SQLException {
        List<CategoryDto> categories = new ArrayList<>();
        try(Connection connection = this.jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT id,name FROM categories");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CategoryDto dto = new CategoryDto();
                dto.setId(rs.getLong(1));
                dto.setName(rs.getString(2));
                categories.add(dto);
            }
        }
        return categories;
    }

    public int countPostsByTitle(String title) throws SQLException {
        try(Connection connection = jdbcTemplate.getDataSource().getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE title LIKE ?");
            ps.setString(1,title);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            if(rs.next()){
                count = rs.getInt(1);
            }
            return count;
        }
    }

}
