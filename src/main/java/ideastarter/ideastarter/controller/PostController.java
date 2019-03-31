package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dao.PostDao;
import ideastarter.ideastarter.model.dao.UserDao;
import ideastarter.ideastarter.model.dto.ShowPostDto;
import ideastarter.ideastarter.model.dto.ShowUserDto;
import ideastarter.ideastarter.model.pojo.Post;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.repository.PostRepository;
import ideastarter.ideastarter.util.exception.BaseException;
import ideastarter.ideastarter.util.exception.PostExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController extends BaseController{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostDao postDao;
    @Autowired
    private UserDao userDao;

    @PostMapping
    public ShowPostDto addPost(@RequestBody Post post, HttpSession session) throws BaseException, SQLException {
        validateLogin(session);
        User user = (User)session.getAttribute("user");
        int count = postRepository.countPostByTitle(post.getTitle());
        if(count>0){
            throw new PostExistsException();
        }
        post.setUser(user);
        postRepository.save(post);
        ShowUserDto showUser = userDao.getUserById(user.getId());
        return new ShowPostDto(post.getId(),post.getTitle(),post.getDescription(),post.getStartDate(),post.getEndDate(),showUser);
    }
    @GetMapping
    public List<ShowPostDto> getPosts(HttpSession session) throws BaseException, SQLException {
        validateLogin(session);
        User user = (User)session.getAttribute("user");
        return postDao.getPostsFromUser(user);
    }

}
