package com.example.test.Repository;

import com.example.test.Model.User;
import com.example.test.Model.UserFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface UserFileRepositoy extends JpaRepository<UserFiles,Integer>
{

}
