package com.example.test.Controller;

import com.example.test.Form.*;
import com.example.test.Model.User;
import com.example.test.Service.AdminService;
import com.example.test.Service.AuthService;
import com.example.test.View.RegisterView;
import com.example.test.View.UserListView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "localhost:4200")
@RequestMapping("/api/admin/")
public class AdminController
{
    @Autowired
    AuthService authService;

    @Autowired
    AdminService adminService;

    @PostMapping("login")
    private ResponseEntity<HttpRex> login(@Valid @RequestBody LoginForm body)
    {
        final JWTForm tokenData = authService.loginUser(body.userName(), body.password());
        HttpRex rex = new HttpRex("200", tokenData);
        return ResponseEntity.ok().body(rex);
    }

    @PostMapping("register")
    private ResponseEntity<HttpRex> signUp(@Valid @RequestBody RegistrationForm body)
    {
        User user = new User();
        user.setUserName(body.userName());
        user.setPassword(body.password());
        user.setRole(body.role());


        final User newUser = authService.registerUser(user);
        final RegisterView registerView = new RegisterView(newUser.getId(), newUser.getUserName(), newUser.getRole());

        HttpRex message = new HttpRex("200", registerView);
        return ResponseEntity.ok().body(message);


    }

    @GetMapping("users")
    private ResponseEntity<HttpRex> listUser()
    {
        final List<User> allUsers = adminService.getAllUsers();
        final List<UserListView> listView = allUsers.stream().map(user -> new UserListView(user.getId(), user.getUserName(), user.getFirstName(),user.getLastName(),user.getRole())).toList();
        HttpRex message = new HttpRex("200", listView);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("createUser")
    private ResponseEntity<HttpRex> createNewUser(@Valid @RequestBody CreateUserForm body)
    {
        try
        {
            User newUser = adminService.createNewUser(body);
            RegisterView view = new RegisterView(newUser.getId(),newUser.getUserName(),newUser.getRole());

            HttpRex message = new HttpRex("200", view);
            return ResponseEntity.ok().body(message);
        }
        catch (DataIntegrityViolationException e)
        {
            HttpRex message = new HttpRex("400", "User Creation Failed, Duplicate username");
            return ResponseEntity.badRequest().body(message);
        }
        catch (Exception e)
        {
//            e.printStackTrace();
            HttpRex message = new HttpRex("500", "User Creation Failed");
            return ResponseEntity.internalServerError().body(message);
        }
    }

    @PutMapping("user/{id}")
    private ResponseEntity<HttpRex> updateUser(@PathVariable Integer id,@Valid @RequestBody UpdateUserForm form)
    {
        User updatedUser = adminService.updateUser(id,form);
        RegisterView view = new RegisterView(updatedUser.getId(),updatedUser.getUserName(),updatedUser.getRole());
        HttpRex message = new HttpRex("200", view);
        return ResponseEntity.ok().body(message);
    }

    @DeleteMapping("user/{id}")
    private ResponseEntity<HttpRex> deleteUser(@PathVariable Integer id)
    {
        adminService.deleteUser(id);
        HttpRex message = new HttpRex("200", "User Deleted");
        return ResponseEntity.ok().body(message);
    }
}
