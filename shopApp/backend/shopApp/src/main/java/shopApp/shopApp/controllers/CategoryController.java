package shopApp.shopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shopApp.shopApp.business.abstracts.CategoryService;
import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;
import shopApp.shopApp.entities.concretes.Category;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {this.categoryService = categoryService;}

    @GetMapping("/getAll")
    public DataResult<List<Category>> getCategories(){return this.categoryService.getCategories();}

    @PostMapping("/add")
    public Result add(@RequestBody Category category){return this.categoryService.add(category);}

    @PutMapping("/update")
    public Result update(@RequestBody Category category,@RequestParam int categoryId){
        return this.categoryService.update(category,categoryId);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int categoryId){
        return this.categoryService.delete(categoryId);

    }
    @GetMapping("/getCategoryById")
    public DataResult<Category> getCategoryById(@RequestParam int categoryId){
        return this.categoryService.getCategoryById(categoryId);
    }
}
