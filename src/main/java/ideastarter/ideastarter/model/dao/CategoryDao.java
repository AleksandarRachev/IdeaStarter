package ideastarter.ideastarter.model.dao;

import ideastarter.ideastarter.model.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryDao {

    @Autowired
    private JdbcTemplate template;

    public Category getCategoryIdByName(String name) throws SQLException {
        Category category = new Category();
        try(Connection connection = template.getDataSource().getConnection()){
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM categories WHERE name LIKE ?");
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                category.setId(rs.getLong(1));
                category.setName(name);
            }
            return category;
        }
    }

}
