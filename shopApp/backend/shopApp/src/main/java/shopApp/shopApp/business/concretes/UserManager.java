package shopApp.shopApp.business.concretes;


import shopApp.shopApp.business.abstracts.UserService;
import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;
import shopApp.shopApp.core.utilities.results.SuccessDataResult;
import shopApp.shopApp.core.utilities.results.SuccessResult;
import shopApp.shopApp.core.utilities.results.ErrorResult;
import shopApp.shopApp.dataAccess.abstracts.UserDao;
import shopApp.shopApp.entities.concretes.User;
import shopApp.shopApp.entities.dtos.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public DataResult<List<UserDTO>> getAll() {
        List<User> users = userDao.findAll();
        List<UserDTO> userDTOs = convertToDTO(users);
        return new SuccessDataResult<>(userDTOs, "Tüm kullanıcılar getirildi.");
    }

    @Override
    public Result add(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        userDao.save(user);
        return new SuccessResult("Kullanıcı eklendi.");
    }

    @Override
    public Result delete(int userId) {
        if (!userDao.existsById(userId)) {
            return new ErrorResult("Kullanıcı bulunamadı.");
        }
        userDao.deleteById(userId);
        return new SuccessResult("Kullanıcı silindi.");
    }

    @Override
    public Result update(int userId, UserDTO userDTO) {
        if (!userDao.existsById(userId)) {
            return new ErrorResult("Güncellenecek kullanıcı bulunamadı.");
        }

        User user = convertToEntity(userDTO);
        user.setId(userId);
        userDao.save(user);
        return new SuccessResult("Kullanıcı başarıyla güncellendi.");
    }

    @Override
    public Result login(String username, String password) {

        User user=userDao.findByUsernameAndPassword(username,password);
        if(user==null){
            return new ErrorResult("kullanıcı bulunamadı");

        }

        if (!user.getPassword().equals(password)) {
            return new ErrorResult( "Şifre hatalı.");
        }

        return new Result(true, "Giriş başarılı.");
    }


    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        return user;
    }


    private List<UserDTO> convertToDTO(List<User> users) {
        return users.stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setPassword(user.getPassword());

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
