package ideastarter.ideastarter.repository;

import ideastarter.ideastarter.model.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    int countUserByUsername(String username);

}
