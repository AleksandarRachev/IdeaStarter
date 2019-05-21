package ideastarter.ideastarter.util;

import ideastarter.ideastarter.model.dao.CategoryDao;
import ideastarter.ideastarter.model.dao.UserDao;
import ideastarter.ideastarter.model.pojo.Category;
import ideastarter.ideastarter.model.pojo.Comment;
import ideastarter.ideastarter.model.pojo.Post;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.repository.CategoryRepository;
import ideastarter.ideastarter.repository.CommentRepository;
import ideastarter.ideastarter.repository.PostRepository;
import ideastarter.ideastarter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class BasicInserts {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws SQLException {
        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEmail("admin@abv.bg");
        user.setPassword(PasswordEncoder.hashPassword("admin"));
        user.setAdmin(true);
        userRepository.save(user);

        User user1 = new User();
        user1.setFirstName("Sasho");
        user1.setLastName("Minchev");
        user1.setEmail("sasho@abv.bg");
        user1.setPassword(PasswordEncoder.hashPassword("123"));
        userRepository.save(user1);

        Category category = new Category();
        category.setName("Travel");
        categoryRepository.save(category);

        Category category1 = new Category();
        category1.setName("Science");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Sports");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setName("Technology");
        categoryRepository.save(category3);


        Post post = new Post();
        Category categ = categoryDao.getCategoryIdByName("Travel");
        post.setTitle("First post");
        post.setDescription("This is the first post ever posted on idea starter by the admin!");
        post.setStartDate(Date.valueOf(LocalDate.now()));
        post.setEndDate(Date.valueOf(LocalDate.now().plusWeeks(1)));
        post.setCategory(categ);
        post.setUser(userRepository.findByEmail("admin@abv.bg"));
        postRepository.save(post);

        Post post1 = new Post();
        Category categ1 = categoryDao.getCategoryIdByName("Science");
        post1.setTitle("Second post");
        post1.setDescription("This is the second post on idea starter by a random user!");
        post1.setStartDate(Date.valueOf(LocalDate.now()));
        post1.setEndDate(Date.valueOf(LocalDate.now().plusWeeks(1)));
        post1.setCategory(categ1);
        post1.setUser(userRepository.findByEmail("sasho@abv.bg"));
        postRepository.save(post1);

        Comment comment = new Comment();
        comment.setComment("First ever comment by user");
        comment.setPost(postRepository.findByTitle("First post"));
        comment.setUser(userRepository.findByEmail("sasho@abv.bg"));
        commentRepository.save(comment);

        Comment comment1 = new Comment();
        comment1.setComment("First ever comment by admin");
        comment1.setPost(postRepository.findByTitle("Second post"));
        comment1.setUser(userRepository.findByEmail("admin@abv.bg"));
        commentRepository.save(comment1);
    }

}
