package ideastarter.ideastarter.repository;

import ideastarter.ideastarter.model.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
