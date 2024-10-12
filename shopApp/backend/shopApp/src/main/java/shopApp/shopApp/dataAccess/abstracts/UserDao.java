package shopApp.shopApp.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import shopApp.shopApp.entities.concretes.User;

public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String password,String username);

}
