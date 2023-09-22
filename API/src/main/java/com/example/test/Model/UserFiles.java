package com.example.test.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user_files")
@Data
public class UserFiles
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(nullable = true)
    private User user;
    private String fileName;
    private Float fileSize;
    @CreationTimestamp
    private LocalDate createdDate;
    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDate updatedDate;
}
