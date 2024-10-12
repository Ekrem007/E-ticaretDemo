package shopApp.shopApp.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shopApp.shopApp.business.abstracts.ProductService;
import shopApp.shopApp.core.utilities.results.*;
import shopApp.shopApp.dataAccess.abstracts.ProductDao;
import shopApp.shopApp.entities.concretes.Category;
import shopApp.shopApp.entities.concretes.Product;
import shopApp.shopApp.entities.dtos.ProductDto;

import java.util.List;
@Service
public class ProductManager implements ProductService {

    private ProductDao productDao;
    @Autowired
    public ProductManager(ProductDao productDao) {this.productDao = productDao;}

    @Override
    public DataResult<List<Product>> getAll() {
        return new SuccessDataResult<List<Product>>(this.productDao.findAll(),"data geldi.");
    }

    @Override
    public Result add(ProductDto productDto) {

        Product product = toEntity(productDto);

        Category category = new Category();
        category.setId(productDto.getCategoryId());
        product.setCategory(category);
        this.productDao.save(product);

        return new SuccessResult("Ürün eklendi");
    }

    @Override
    public Result delete(int productId) {
        this.productDao.deleteById(productId);
        return new SuccessResult("ürün silindi");
    }

    @Override
    public Result update(int productId, ProductDto productDto) {
        Product existingProduct = this.productDao.findById(productId).orElse(null);

        if (existingProduct == null) {
            return new ErrorResult("Güncellenecek ürün bulunamadı.");
        }

        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setImageUrl(productDto.getImageUrl());

        Category category = new Category();
        category.setId(productDto.getCategoryId());
        existingProduct.setCategory(category);

        this.productDao.save(existingProduct);
        return new SuccessResult("Ürün başarıyla güncellendi.");
    }


    @Override
    public DataResult<List<Product>> getByCategoryId(int categoryId) {
        List<Product> products = this.productDao.getByCategoryId(categoryId);
        if (products.isEmpty()){
            return new ErrorDataResult<List<Product>>(" seçilen kategoriye ait ürün bulunamadı");
        }

        return  new SuccessDataResult<List<Product>>(products, "Data geldi");
    }

    @Override
    public DataResult<Product> getProductById(int productId) {
        return new SuccessDataResult<Product>(this.productDao.getProductById(productId));
    }
    public static Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());


        Category category = new Category();
        product.setCategory(category);

        return product;
    }

    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setCategoryId(product.getCategory().getId());

        return productDto;
    }
}
