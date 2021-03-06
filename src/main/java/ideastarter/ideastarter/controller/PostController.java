package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dao.CategoryDao;
import ideastarter.ideastarter.model.dao.CommentDao;
import ideastarter.ideastarter.model.dao.PostDao;
import ideastarter.ideastarter.model.dao.UserDao;
import ideastarter.ideastarter.model.dto.*;
import ideastarter.ideastarter.model.pojo.Category;
import ideastarter.ideastarter.model.pojo.Post;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.repository.PostRepository;
import ideastarter.ideastarter.util.SuccessMessage;
import ideastarter.ideastarter.util.exception.BaseException;
import ideastarter.ideastarter.util.exception.PostExistsException;
import ideastarter.ideastarter.util.exception.PostHasIncomeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
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
    @Autowired
    private CommentDao commentDao;

    @PostMapping
    public ShowPostDto addPost(HttpServletResponse response,HttpServletRequest request, HttpSession session) throws BaseException, SQLException, ParseException, IOException {
        validateLogin(session,response);
        Post post = new Post();
        User user = (User) session.getAttribute("user");
        int count = postDao.countPostsByTitle(request.getParameter("title"));
        if (count > 0) {
            response.sendRedirect("http://localhost:9999/postExists.html");
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
        response.sendRedirect("http://localhost:9999/posts.html");
        return new ShowPostDto(post.getId(), post.getTitle(), post.getDescription(), post.getStartDate(), post.getEndDate(), showUser);
    }

    @GetMapping(value = "/{id}")
    public List<ShowPostNoUserDto> getPosts(@PathVariable("id") Long userId) throws SQLException {
        return postDao.getPostsForUser(userId);
    }

    @DeleteMapping(value = "/{id}")
    @Transactional
    public SuccessMessage deletePost(@PathVariable("id") Long postId,HttpServletResponse response) throws SQLException, PostHasIncomeException, IOException {
        ShowPostNoUserDto post = postDao.getPostById(postId);
        if(post.getDonates() > 0){
            throw new PostHasIncomeException();
        }
        commentDao.deleteCommentsByPostId(postId);
        postRepository.deleteById(postId);
        response.sendRedirect("http://localhost:9999/profile.html");
        return new SuccessMessage("Post deleted successfully",LocalDate.now());
    }

    @GetMapping(value = "/top")
    public List<ShowPostNoUserDto> getTop5Posts(HttpServletResponse response, HttpServletRequest request) throws SQLException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        return postDao.getTop5Posts();
    }

    @GetMapping(value = "/all")
    public List<ShowPostNoUserDto> getAllPosts(HttpServletResponse response, HttpServletRequest request) throws SQLException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        return postDao.getAllPosts();
    }

    @GetMapping(value = "/categories")
    public List<CategoryDto> getCategories(HttpServletResponse response, HttpServletRequest request) throws SQLException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        return postDao.getCategories();
    }

    @PostMapping(value = "/donate/{id}")
    public SuccessMessage donateToPost(@PathVariable("id") Long postId, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
        double donate = Double.parseDouble(request.getParameter("donate"));
        postDao.takeDonation(postId,donate);
        response.sendRedirect("http://localhost:9999/posts.html");
        return new SuccessMessage("Donate successful", LocalDate.now());
    }

    @GetMapping(value = "/info")
    public GeneralInfoDto getInfo() throws SQLException {
        GeneralInfoDto generalInfoDto = new GeneralInfoDto();
        generalInfoDto.setUserCount(userDao.getTotalUsers());
        generalInfoDto.setPostsCount(postDao.getTotalPosts());
        generalInfoDto.setCommentsWritten(commentDao.getTotalComments());
        return generalInfoDto;
    }

    @GetMapping(value = "/category/{id}")
    public List<ShowPostNoUserDto> getAllPosts(@PathVariable("id")Long categId, HttpServletResponse response, HttpServletRequest request) throws SQLException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        return postDao.getPostsPerCategory(categId);
    }

}