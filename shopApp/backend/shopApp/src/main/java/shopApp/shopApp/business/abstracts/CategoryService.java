package shopApp.shopApp.business.abstracts;

import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;
import shopApp.shopApp.entities.concretes.Category;

import java.util.List;

public interface CategoryService {

    DataResult<List<Category>> getCategories();

    Result add(Category category);
    Result delete(int categoryId);
    Result update(Category category, int categoryId);
    DataResult<Category> getCategoryById(int categoryId);

}
