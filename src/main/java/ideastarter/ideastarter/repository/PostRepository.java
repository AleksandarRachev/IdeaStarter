package ideastarter.ideastarter.repository;

import ideastarter.ideastarter.model.pojo.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

    int countPostByTitle(String title);

    Post findByTitle(String title);

}
