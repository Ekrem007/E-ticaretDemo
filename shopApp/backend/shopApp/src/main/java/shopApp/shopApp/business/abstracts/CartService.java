package shopApp.shopApp.business.abstracts;

import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;
import shopApp.shopApp.entities.dtos.CartDto;

import java.util.List;

public interface CartService {
    DataResult<List<CartDto>> getWaitingCarts();
    DataResult<List<CartDto>> getSendedCarts();
    DataResult<List<CartDto>> getAll();

    Result add(CartDto cartDto);
    Result delete(int CartDtoId);
    Result update(CartDto cartDto, int CartDtoId);
    DataResult<Double> getTotalPriceByUserId(int userId);
    DataResult<Integer> getTotalQuantityByUserId(int userId);
    Result orderCart();
    DataResult <List<CartDto>> getOrders();
    Result sendOrder(int cartId);
}
