package com.example.test.Service;

import com.example.test.DTO.CreateUserForm;
import com.example.test.Model.User;
import com.example.test.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService
{
    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createNewUser(CreateUserForm userForm)
    {
        User newUser = new User();
        newUser.setUserName(userForm.userName());
        newUser.setFirstName(userForm.firstName());
        newUser.setLastName(userForm.lastName());
        newUser.setRole("USER");
        newUser.setPassword(passwordEncoder.encode(userForm.password()));

        return userRepo.save(newUser);
    }
}
