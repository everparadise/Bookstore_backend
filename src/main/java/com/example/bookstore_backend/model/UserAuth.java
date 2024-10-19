package com.example.bookstore_backend.model;

import com.example.bookstore_backend.Constants.Role;
import jakarta.persistence.*;

@Table(name = "authusers")
@Entity
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uid")
    private User user;

}
