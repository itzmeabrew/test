package com.example.test.Service;

import com.example.test.Exception.HttpRuntimeException;
import com.example.test.Form.CreateUserForm;
import com.example.test.Form.UpdateUserForm;
import com.example.test.Model.User;
import com.example.test.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        final List<User> allUsers= userRepo.findByRoleOrderByUserNameAsc("USER");
        return Objects.requireNonNullElseGet(allUsers, ArrayList::new);
    }

    public void deleteUser(Integer id)
    {
        userRepo.deleteById(id);
    }

    public User updateUser(Integer id, UpdateUserForm form)
    {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent())
        {
            User updatedUser = user.get();
            updatedUser.setId(id);
            updatedUser.setUserName(form.userName());
            updatedUser.setFirstName(form.firstName());
            updatedUser.setLastName(form.lastName());
            if(StringUtils.hasText(form.password()))
            {
                updatedUser.setPassword(passwordEncoder.encode(form.password()));
            }
            return userRepo.save(updatedUser);
        }
        else
        {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND,"No user found with that id");
        }
    }
}
