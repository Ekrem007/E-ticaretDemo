package shopApp.shopApp.dataAccess.abstracts;

import shopApp.shopApp.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product,Integer> {
    List<Product> getByCategoryId(int CategoryId);
    Product getProductById(int productId);


}
