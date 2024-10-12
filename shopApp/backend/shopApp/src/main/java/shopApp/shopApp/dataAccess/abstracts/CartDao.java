package shopApp.shopApp.dataAccess.abstracts;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shopApp.shopApp.entities.concretes.Cart;
import shopApp.shopApp.entities.concretes.OrderStatus;

import java.util.List;

@Repository
public interface CartDao extends JpaRepository<Cart,Integer> {

        List<Cart> findByUserId(int userId);
        List<Cart>findByUserIdAndProductId(int userId, int productId);
        List<Cart> findByStatus(OrderStatus status);
        @Modifying
        @Transactional
        @Query("UPDATE Cart c SET c.status = shopApp.shopApp.entities.concretes.OrderStatus.REQUEST_SENDED WHERE c.status = shopApp.shopApp.entities.concretes.OrderStatus.WAITING")
        void updateStatusToSendedForWaitingCarts();
        @Query("SELECT c FROM Cart c WHERE c.status <> :status")
        List<Cart> findAllExceptWaiting(OrderStatus status);


}


