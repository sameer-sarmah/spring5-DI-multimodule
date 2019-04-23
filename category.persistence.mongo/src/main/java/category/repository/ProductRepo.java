package category.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import category.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String>{

}


