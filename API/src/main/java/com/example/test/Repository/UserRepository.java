package com.example.test.Repository;

import com.example.test.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
    User findByUserName(String userName);
    Optional<User> findById(Integer id);
    Boolean existsByUserName(String userName);
    void deleteById(Integer id);
}
