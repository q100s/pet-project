package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Поиск всех пользователей, находящихся в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Коллекция всех юзеров, существующих в базе данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User[].class))
                            )
                            }
                    )
            })
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();

    }


    @Operation(
            summary = "Добавление пользователя в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавляемый пользователь",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь добавлен в базу данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    )
            })
    @PostMapping
    public User createUser(@Parameter(description = "Добавляемый пользователь") User user) {
        return userService.createUser(user);
    }


    @Operation(
            summary = "Изменение пользователя в базе данных по искомому идентификатору",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отредактированный пользователь",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь с переданным идентификатором изменен",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором не существует"
                    )
            })
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@Parameter(description = "Идентификатор для поиска") @PathVariable Long id,
                                         @Parameter(description = "Отредактированный пользователь") @RequestBody User user) {
        User shelterUserCheck = userService.editUser(id, user);
        if (shelterUserCheck == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Удаление пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь с переданным идентификатором удален",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором отсутствует в базе данных"
                    )
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<User> removeUser(@Parameter(description = "Идентификатор для поиска") @PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Поиск пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь, найденный по идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователя с переданным идентификатором отсутствует в базе данных"
                    )
            })
    @GetMapping("/{id}")
    public User findUserById(@Parameter(description = "Идентификатор для поиска") @PathVariable long id) {
        return userService.findUserById(id);
    }




}
