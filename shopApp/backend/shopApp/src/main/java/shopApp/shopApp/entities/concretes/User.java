package shopApp.shopApp.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;
}
