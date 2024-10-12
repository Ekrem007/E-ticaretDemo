package shopApp.shopApp.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","products"})
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="price")
    private double price;
    @Column(name="description")
    private String description;
    @Column(name="image_url")
    private String imageUrl;


    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
