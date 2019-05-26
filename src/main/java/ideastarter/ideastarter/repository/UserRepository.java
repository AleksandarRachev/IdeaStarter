package ideastarter.ideastarter.repository;

import ideastarter.ideastarter.model.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    int countUserByEmail(String email);

}
