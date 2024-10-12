package shopApp.shopApp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopApp.shopApp.business.abstracts.ProductService;
import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;
import shopApp.shopApp.core.utilities.results.SuccessDataResult;
import shopApp.shopApp.entities.concretes.Product;
import shopApp.shopApp.entities.dtos.ProductDto;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {this.productService = productService;}

    @GetMapping("/getAll")
    public DataResult<List<Product>> getAll() {
        return this.productService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody ProductDto productDto){return this.productService.add(productDto);}


    @DeleteMapping("/delete")
    public Result delete (@RequestParam int productId){
        return this.productService.delete(productId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody ProductDto productDto,@RequestParam int productId){
        return this.productService.update(productId,productDto);
    }
    @GetMapping("/getByCategory")
    public DataResult<List<Product>> getByCategoryId(@RequestParam int categoryId){
        return this.productService.getByCategoryId(categoryId);
    }
    @GetMapping("/getByProductId")
    public DataResult<Product>getByProductId(@RequestParam int productId){
        return this.productService.getProductById(productId);
    }
}


