package com.example.test.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDate createdDate;
    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDate updatedDate;
}
