package category.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import category.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, String>{

}
