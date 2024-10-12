package shopApp.shopApp.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shopApp.shopApp.business.abstracts.CategoryService;
import shopApp.shopApp.core.utilities.results.*;
import shopApp.shopApp.dataAccess.abstracts.CategoryDao;
import shopApp.shopApp.entities.concretes.Category;

import java.util.List;

@Service
public class CategoryManager implements CategoryService {
    private CategoryDao categoryDao;
    @Autowired
    public CategoryManager(CategoryDao categoryDao) {this.categoryDao = categoryDao;}


    @Override
    public DataResult<List<Category>> getCategories() {

        return new SuccessDataResult<List<Category>>(this.categoryDao.findAll(),"Data getirildi.");
    }

    @Override
    public Result add(Category category) {
        this.categoryDao.save(category);
        return new SuccessResult("category eklendi");
    }

    @Override
    public Result delete(int categoryId) {
        this.categoryDao.deleteById(categoryId);
        return new SuccessResult("category silindi");
    }

    @Override
    public Result update(Category category, int categoryId) {
        Category newCategory= categoryDao.findById(categoryId).orElse(null);
        if(newCategory==null ){
            return new ErrorResult("güncellenecek kategori bulunamadı.");

        }
        newCategory.setName(category.getName());
        categoryDao.save(newCategory);
        return new SuccessResult("ürün güncellendi");
    }

    @Override
    public DataResult<Category> getCategoryById(int categoryId) {
        return new SuccessDataResult<Category>(this.categoryDao.getCategoryById(categoryId));
    }


}
