package shopApp.shopApp.controllers;

import shopApp.shopApp.business.abstracts.UserService;
import shopApp.shopApp.core.utilities.results.DataResult;
import shopApp.shopApp.core.utilities.results.Result;
import shopApp.shopApp.entities.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public DataResult<List<UserDTO>> getAll() {
        return userService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody UserDTO userDTO) {
        return userService.add(userDTO);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int userId) {
        return userService.delete(userId);
    }

    @PutMapping("/update")
    public Result update(@RequestParam int userId, @RequestBody UserDTO userDTO) {
        return userService.update(userId, userDTO);
    }
    @PostMapping("/login")
    public Result login(@RequestParam String username,@RequestParam String password) {
        return userService.login(username,password);
    }
}
