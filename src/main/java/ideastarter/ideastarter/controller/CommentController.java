package ideastarter.ideastarter.controller;

import ideastarter.ideastarter.model.dao.CommentDao;
import ideastarter.ideastarter.model.dto.ShowCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    private CommentDao commentDao;

    @GetMapping(value = "/{id}")
    public List<ShowCommentDto> getCommentsForPost(@PathVariable("id") Long postId) throws SQLException {
        return commentDao.getAllCommentsForPost(postId);
    }

}
