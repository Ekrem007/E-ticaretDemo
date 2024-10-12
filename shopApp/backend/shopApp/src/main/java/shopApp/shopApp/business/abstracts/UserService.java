package shopApp.shopApp.business.abstracts;

import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;

import shopApp.shopApp.entities.dtos.UserDTO;

import java.util.List;

public interface UserService {
    DataResult<List<UserDTO>> getAll();

    Result add(UserDTO userDTO);
    Result delete(int userId);

    Result update(int userId, UserDTO userDTO);
    Result login(String username,String password);

}
