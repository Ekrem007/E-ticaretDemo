package shopApp.shopApp.business.abstracts;

import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;
import shopApp.shopApp.entities.concretes.Category;
import shopApp.shopApp.entities.concretes.Product;
import shopApp.shopApp.entities.dtos.ProductDto;

import java.util.List;

public interface ProductService  {

    DataResult<List<Product>> getAll();

    Result add(ProductDto productDto);
    Result delete(int productId);

    Result update(int productId,ProductDto productDto);
    DataResult<List<Product>> getByCategoryId(int categoryId);
    DataResult<Product>getProductById(int productId);
}
