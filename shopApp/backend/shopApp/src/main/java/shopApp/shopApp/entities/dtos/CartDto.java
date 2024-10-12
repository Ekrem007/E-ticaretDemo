package shopApp.shopApp.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shopApp.shopApp.entities.concretes.OrderStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDto {

    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private Double totalPrice;
    private OrderStatus status;
    private String productName;
    private double productPrice;
    private String imageUrl;
}
