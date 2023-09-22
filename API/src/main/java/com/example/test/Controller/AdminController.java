package com.example.test.Controller;

import com.example.test.DTO.HttpRex;
import com.example.test.DTO.JWTForm;
import com.example.test.DTO.LoginForm;
import com.example.test.DTO.NewUserForm;
import com.example.test.Exception.HttpEx;
import com.example.test.Model.User;
import com.example.test.Security.JwtTokenProvider;
import com.example.test.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

@RestController
@RequestMapping("/api/admin/")
public class AdminController
{
    @Autowired
    AuthService authService;

    @PostMapping("login")
    private ResponseEntity<HttpRex> login(@Valid@RequestBody LoginForm body)
    {
        final JWTForm tokenData = authService.loginUser(body.userName(), body.password());
        HttpRex rex = new HttpRex("200", tokenData);
        return ResponseEntity.ok().body(rex);
    }

    @PostMapping("register")
    private ResponseEntity<HttpRex> signUp(@Valid @RequestBody NewUserForm body)
    {
        User user = new User();
        user.setUserName(body.userName());
        user.setPassword(body.password());
        user.setRole(body.role());

        try
        {
            authService.createUser(user);
            HttpRex message = new HttpRex("200",user);
            return ResponseEntity.ok().body(message);
        }
        catch (HttpEx e)
        {
            HttpRex message = new HttpRex("409","User already exsists");
            return ResponseEntity.status(409).body(message);
        }
    }
}
