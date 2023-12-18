package ru.skypro.homework.model;

import lombok.Data;
import lombok.ToString;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @Min(8)
    @Max(16)
    private String password;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "(\\+\\d{1,3}( )?)?")
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;


}
