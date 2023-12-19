package ru.skypro.homework.model;

import lombok.Data;
import ru.skypro.homework.constant.Role;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String imageUrl;

    @OneToMany(mappedBy = "author")
    private Collection<Ad> ads;

    @OneToMany(mappedBy = "author")
    private Collection<Comment> comments;
}