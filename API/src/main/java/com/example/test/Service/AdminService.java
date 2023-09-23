package com.example.test.Service;

import com.example.test.Form.CreateUserForm;
import com.example.test.Model.User;
import com.example.test.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<User> getAllUsers()
    {
        final List<User> allUsers= userRepo.findByRole("USER");
        if(allUsers == null)
        {
            return new ArrayList<User>();
        }
        else
        {
            return allUsers;
        }
    }
}
