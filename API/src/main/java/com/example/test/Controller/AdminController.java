package com.example.test.Controller;

import com.example.test.DTO.*;
import com.example.test.Exception.HttpEx;
import com.example.test.Model.User;
import com.example.test.Service.AdminService;
import com.example.test.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

        try
        {
            User newUser = authService.createUser(user);
            HttpRex message = new HttpRex("200",newUser);
            return ResponseEntity.ok().body(message);
        }
        catch (HttpEx e)
        {
            HttpRex message = new HttpRex("409","User already exsists");
            return ResponseEntity.status(409).body(message);
        }
    }

    @PostMapping("createUser")
    private ResponseEntity<HttpRex> createNewUser(@Valid @RequestBody CreateUserForm body)
    {
        try
        {
            User newUser = adminService.createNewUser(body);
            HttpRex message = new HttpRex("200",newUser);
            return ResponseEntity.ok().body(message);
        }
        catch (Exception e)
        {
            HttpRex message = new HttpRex("500","User Creation Failed");
            return ResponseEntity.internalServerError().body(message);
        }
    }
}
