package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dao.CommentDao;
import ideastarter.ideastarter.model.dto.ShowCommentDto;
import ideastarter.ideastarter.model.pojo.User;
import ideastarter.ideastarter.util.SuccessMessage;
import ideastarter.ideastarter.util.exception.BaseException;
import ideastarter.ideastarter.util.exception.InvalidCommentException;
import ideastarter.ideastarter.util.exception.NotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController extends BaseController {

    @Autowired
    private CommentDao commentDao;

    @GetMapping(value = "/{id}")
    public List<ShowCommentDto> getCommentsForPost(@PathVariable("id") Long postId) throws SQLException {
        return commentDao.getAllCommentsForPost(postId);
    }

    @PostMapping(value = "/{id}")
    public SuccessMessage putCommentOnPost(@PathVariable("id") Long postId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws BaseException, SQLException, IOException {
        validateLogin(session,response);
        User user = (User) session.getAttribute("user");
        String comment = request.getParameter("comment");
        if(comment.isEmpty() || comment.equals(" ") || comment.equals("\n")){
            throw new InvalidCommentException();
        }
        commentDao.putCommentOnPost(comment, postId, user.getId());
        response.sendRedirect("http://localhost:9999/posts.html");
        return new SuccessMessage("Comment added successfully", LocalDate.now());
    }

    @GetMapping(value = "/distinct/{id}")
    public SuccessMessage getDistinctComments(@PathVariable("id") Long postId) throws SQLException {
        int count = commentDao.getDistinctComments(postId);
        return new SuccessMessage(""+count,LocalDate.now());
    }

}
