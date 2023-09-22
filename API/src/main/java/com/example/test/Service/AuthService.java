package com.example.test.Service;

import com.example.test.Exception.HttpEx;
import com.example.test.Model.User;
import com.example.test.Repository.UserRepository;
import com.example.test.Security.JwtTokenProvider;
import com.example.test.Util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService
{
    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public User createUser(User user) throws HttpEx
    {
        final String userName = user.getUserName();

        if(userRepo.existsByUserName(userName))
        {
            throw new HttpEx(HttpStatus.CONFLICT,"409","User already exsists");
        }
        else
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("ADMIN");
            return userRepo.save(user);
        }
    }

    public String loginUser(String userName,String password)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // get token form tokenProvider
        return tokenProvider.generateToken(authentication);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        final User user = userRepo.findByUserName(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        else
        {
            return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(), AuthUtils.createAuthority(user.getRole()));
        }
    }
}
