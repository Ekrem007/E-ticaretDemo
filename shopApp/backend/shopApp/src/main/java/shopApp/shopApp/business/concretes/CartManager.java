    package shopApp.shopApp.business.concretes;

    import jakarta.transaction.Transactional;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import shopApp.shopApp.business.abstracts.CartService;
    import shopApp.shopApp.core.utilities.results.*;
    import shopApp.shopApp.dataAccess.abstracts.CartDao;
    import shopApp.shopApp.dataAccess.abstracts.ProductDao;
    import shopApp.shopApp.dataAccess.abstracts.UserDao;
    import shopApp.shopApp.entities.concretes.Cart;
    import shopApp.shopApp.entities.concretes.OrderStatus;
    import shopApp.shopApp.entities.concretes.Product;
    import shopApp.shopApp.entities.concretes.User;
    import shopApp.shopApp.entities.dtos.CartDto;

    import java.util.List;

    @Service
    public class CartManager implements CartService {

        private CartDao cartDao;
        private UserDao userDao;
        private ProductDao productDao;

        @Autowired
        public CartManager(CartDao cartDao, UserDao userDao, ProductDao productDao) {
            this.cartDao = cartDao;
            this.userDao = userDao;
            this.productDao = productDao;
        }
        @Override
        public DataResult<List<CartDto>> getAll() {

            List<Cart> carts = cartDao.findAllExceptWaiting(OrderStatus.WAITING);
            List<CartDto> cartDtos = carts.stream()
                    .map(this::convertToDto)
                    .toList();

            return new SuccessDataResult<>(cartDtos, "Data başarıyla alındı.");
        }

        @Override
        public DataResult<List<CartDto>> getWaitingCarts() {

            List<Cart> carts = cartDao.findByStatus(OrderStatus.WAITING);
            List<CartDto> cartDtos = carts.stream()
                    .map(this::convertToDto)
                    .toList();

            return new SuccessDataResult<>(cartDtos, "Data başarıyla alındı.");
        }
        @Override
        public DataResult<List<CartDto>> getSendedCarts() {

            List<Cart> carts = cartDao.findByStatus(OrderStatus.REQUEST_SENDED);
            List<CartDto> cartDtos = carts.stream()
                    .map(this::convertToDto)
                    .toList();

            return new SuccessDataResult<>(cartDtos, "Data başarıyla alındı.");
        }





        @Override
        public Result add(CartDto cartDto) {
            Product product = this.productDao.findById(cartDto.getProductId()).orElse(null);
            if (product == null) {
                return new ErrorResult("Ürün bulunamadı.");
            }

            User user = this.userDao.findById(cartDto.getUserId()).orElse(null);
            if (user == null) {
                return new ErrorResult("Kullanıcı bulunamadı.");
            }

            List<Cart> existingCarts = this.cartDao.findByUserIdAndProductId(user.getId(), product.getId());

            if (!existingCarts.isEmpty()) {
                Cart existingCart = existingCarts.get(0);

                if (existingCart.getStatus() == OrderStatus.WAITING) {
                    existingCart.setQuantity(existingCart.getQuantity() + 1);
                    double newTotalPrice = product.getPrice() * existingCart.getQuantity();
                    existingCart.setTotalPrice(newTotalPrice);
                    this.cartDao.save(existingCart);
                    return new SuccessResult("Sepetteki ürün miktarı artırıldı.");
                } else {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setProduct(product);
                    newCart.setQuantity(1);
                    double totalPrice = product.getPrice();
                    newCart.setTotalPrice(totalPrice);
                    newCart.setStatus(OrderStatus.WAITING);

                    this.cartDao.save(newCart);
                    return new SuccessResult("Sepete yeni ürün eklendi.");
                }
            }

            Cart cart = convertToEntity(cartDto, user, product);
            if (cart == null) {
                return new ErrorResult("Sepet oluşturulurken bir hata oluştu.");
            }

            cart.setQuantity(1);
            double totalPrice = product.getPrice() * cart.getQuantity();
            cart.setTotalPrice(totalPrice);

            cart.setStatus(OrderStatus.WAITING);

            this.cartDao.save(cart);
            return new SuccessResult("Sepete ürün eklendi.");
        }





        @Override
        public Result delete(int cartId) {
            Cart existingCart = this.cartDao.findById(cartId).orElse(null);
            if (existingCart == null) {
                return new ErrorResult("Sepette ürün bulunamadı.");
            }

            if (existingCart.getQuantity() > 1) {
                existingCart.setQuantity(existingCart.getQuantity() - 1);
                double newTotalPrice = existingCart.getProduct().getPrice() * existingCart.getQuantity();
                existingCart.setTotalPrice(newTotalPrice);

                this.cartDao.save(existingCart);
                return new SuccessResult("Sepetten ürün miktarı 1 azaltıldı.");
            } else {
                this.cartDao.deleteById(cartId);
                return new SuccessResult("Sepetten ürün silindi.");
            }
        }




        @Override
        public Result update(CartDto cartDto, int cartId) {
            Cart existingCart = this.cartDao.findById(cartId).orElse(null);
            if (existingCart == null) {
                return new ErrorResult("Güncellenecek sepet bulunamadı.");
            }

            existingCart.setTotalPrice(cartDto.getTotalPrice());
            existingCart.setStatus(cartDto.getStatus());

            this.cartDao.save(existingCart);
            return new SuccessResult("Sepet başarıyla güncellendi.");
        }


        @Override
        public DataResult<Double> getTotalPriceByUserId(int userId) {
            List<Cart> carts = cartDao.findByUserId(userId);

            double totalPrice = carts.stream()
                    .filter(cart -> cart.getStatus() == OrderStatus.WAITING)
                    .mapToDouble(Cart::getTotalPrice)
                    .sum();

            return new SuccessDataResult<>(totalPrice, "Toplam fiyat başarıyla hesaplandı.");
        }


        @Override
        public DataResult<Integer> getTotalQuantityByUserId(int userId) {
            List<Cart> carts = cartDao.findByUserId(userId);

            int totalQuantity = carts.stream()
                    .filter(cart -> cart.getStatus() == OrderStatus.WAITING)
                    .mapToInt(Cart::getQuantity)
                    .sum();

            return new SuccessDataResult<>(totalQuantity, "Toplam quantity başarıyla hesaplandı.");
        }


        @Override
        @Transactional
        public DataResult<String> orderCart() {
            cartDao.updateStatusToSendedForWaitingCarts();
            return new SuccessDataResult<>("Sipariş başarıyla tamamlandı.");
        }
        @Override
        public Result sendOrder(int cartId) {
            Cart existingCart = this.cartDao.findById(cartId).orElse(null);
            if (existingCart == null) {
                return new ErrorResult("Güncellenecek sepet bulunamadı.");
            }

            if (existingCart.getStatus() != OrderStatus.REQUEST_SENDED) {
                return new ErrorResult("Sadece SENDED olan sepetler tamamlanabilir.");
            }

            existingCart.setStatus(OrderStatus.COMPLETED);
            this.cartDao.save(existingCart);

            return new SuccessResult("Sepet durumu başarıyla tamamlandı.");
        }


        @Override
        public DataResult <List<CartDto>> getOrders() {
            List<Cart> carts = cartDao.findByStatus(OrderStatus.REQUEST_SENDED);

            List<CartDto> cartDtos = carts.stream()
                    .map(this::convertToDto)
                    .toList();

            return new SuccessDataResult<>(cartDtos, "Data başarıyla alındı.");
        }


        public CartDto convertToDto(Cart cart) {
            CartDto cartDto = new CartDto();
            cartDto.setId(cart.getId());
            cartDto.setQuantity(cart.getQuantity());
            cartDto.setTotalPrice(cart.getProduct().getPrice() * cart.getQuantity());

            cartDto.setStatus(cart.getStatus());

            cartDto.setProductName(cart.getProduct().getName());
            cartDto.setProductPrice(cart.getProduct().getPrice());
            cartDto.setImageUrl(cart.getProduct().getImageUrl());

            if (cart.getUser() != null) {
                cartDto.setUserId(cart.getUser().getId());
            }

            if (cart.getProduct() != null) {
                cartDto.setProductId(cart.getProduct().getId());
            }

            return cartDto;
        }


        public Cart convertToEntity(CartDto cartDto, User user, Product product) {
            Cart cart = new Cart();
            cart.setId(cartDto.getId());
            cart.setQuantity(cartDto.getQuantity());
            cart.setTotalPrice(product.getPrice() * cartDto.getQuantity());
            cart.setUser(user);
            cart.setProduct(product);

            cart.setStatus(cartDto.getStatus());

            return cart;
        }
    }
