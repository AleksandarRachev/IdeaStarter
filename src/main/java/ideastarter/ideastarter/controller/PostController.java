package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dao.CategoryDao;
import ideastarter.ideastarter.model.dao.PostDao;
import ideastarter.ideastarter.model.dao.UserDao;
import ideastarter.ideastarter.model.dto.CategoryDto;
import ideastarter.ideastarter.model.dto.ShowPostDto;
import ideastarter.ideastarter.model.dto.ShowPostNoUserDto;
import ideastarter.ideastarter.model.dto.ShowUserDto;
import ideastarter.ideastarter.model.pojo.Category;
import ideastarter.ideastarter.model.pojo.Post;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.repository.PostRepository;
import ideastarter.ideastarter.util.SuccessMessage;
import ideastarter.ideastarter.util.exception.BaseException;
import ideastarter.ideastarter.util.exception.PostExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/posts", produces = {"application/json"})
public class PostController extends BaseController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostDao postDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CategoryDao categoryDao;

    @PostMapping
    public ShowPostDto addPost(HttpServletRequest request, HttpSession session) throws BaseException, SQLException, ParseException {
        validateLogin(session);
        Post post = new Post();
        User user = (User) session.getAttribute("user");
        int count = postDao.countPostsByTitle(request.getParameter("title"));
        if (count > 0) {
            throw new PostExistsException();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryName = request.getParameter("category");
        Date startDate = simpleDateFormat.parse(request.getParameter("startDate"));
        Date endDate = simpleDateFormat.parse(request.getParameter("endDate"));
        if (startDate.after(endDate)) {
            throw new BaseException("Wrong date");
        }
        Category category = categoryDao.getCategoryIdByName(categoryName);
        post.setTitle(title);
        post.setDescription(description);
        post.setStartDate(startDate);
        post.setEndDate(endDate);
        post.setCategory(category);
        post.setUser(user);

        postRepository.save(post);
        ShowUserDto showUser = userDao.getUserById(user.getId());
        return new ShowPostDto(post.getId(), post.getTitle(), post.getDescription(), post.getStartDate(), post.getEndDate(), showUser);
    }

    @GetMapping
    public List<ShowPostDto> getPosts(HttpSession session) throws BaseException, SQLException {
        validateLogin(session);
        User user = (User) session.getAttribute("user");
        return postDao.getPostsFromUser(user);
    }

    @GetMapping(value = "/all")
    public List<ShowPostNoUserDto> getAllPosts(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws SQLException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        return postDao.getAllPosts();
    }

    @GetMapping(value = "/categories")
    public List<CategoryDto> getCategories(HttpServletResponse response, HttpServletRequest request) throws SQLException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        return postDao.getCategories();
    }

    @PostMapping(value = "/donate/{id}")
    public SuccessMessage donateToPost(@PathVariable("id") Long postId, HttpServletRequest request) throws SQLException {
        double donate = Double.parseDouble(request.getParameter("donate"));
        postDao.takeDonation(postId,donate);
        return new SuccessMessage("Donate successful", LocalDate.now());
    }

}
