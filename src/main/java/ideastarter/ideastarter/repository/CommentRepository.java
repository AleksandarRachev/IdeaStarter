package ideastarter.ideastarter.repository;

import ideastarter.ideastarter.model.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteAllByPostId(Long postId);

}
