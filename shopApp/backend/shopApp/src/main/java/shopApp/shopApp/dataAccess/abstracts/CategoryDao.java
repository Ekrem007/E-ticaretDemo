package shopApp.shopApp.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.shopApp.entities.concretes.Category;



public interface CategoryDao extends JpaRepository<Category,Integer> {

    Category getCategoryById (int categoryId);

}
