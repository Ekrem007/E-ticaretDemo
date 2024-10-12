package shopApp.shopApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shopApp.shopApp.business.abstracts.CartService;
import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;
import shopApp.shopApp.entities.dtos.CartDto;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CartDto>> getAll() {
        return this.cartService.getAll();
    }

    @GetMapping("/getWaitingCarts")
    public DataResult<List<CartDto>> getWaitingCarts() {
        return this.cartService.getWaitingCarts();
    }
    @GetMapping("/getSendedCarts")
    public DataResult<List<CartDto>> getSendedCarts() {
        return this.cartService.getSendedCarts();
    }
    @PutMapping("/{cartId}/complete")
    public Result markCartAsCompleted(@PathVariable int cartId) {
        return cartService.sendOrder(cartId);
    }
    @PostMapping("/add")
    public Result add(@RequestBody CartDto cartDto) {
        return this.cartService.add(cartDto);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int cartDtoId) {
        return this.cartService.delete(cartDtoId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody CartDto cartDto, @RequestParam int cartDtoId) {
        return this.cartService.update(cartDto, cartDtoId);
    }
    @GetMapping("/totalPrice")
    public DataResult<Double> getTotalPrice(@RequestParam int userId) {
        return this.cartService.getTotalPriceByUserId(userId);
    }
    @GetMapping("/totalQuantity")
    public DataResult<Integer> getTotalQuantityByUserId(@RequestParam int userId) {
        return this.cartService.getTotalQuantityByUserId(userId);
    }
    @PostMapping("/order")
    public Result orderCart() {
        return this.cartService.orderCart();
    }
    @GetMapping("/getOrders")
    public DataResult<List<CartDto>> getOrders(){
        return this.cartService.getOrders();
    }


}
