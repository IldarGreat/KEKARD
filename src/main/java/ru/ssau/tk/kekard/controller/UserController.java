package ru.ssau.tk.kekard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.tk.kekard.entity.UserDto;
import ru.ssau.tk.kekard.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/login", produces = "application/json; charset=UTF-8")
    @Operation(summary = "Авторизация", description = "Возвращает JWT, который нужно прикладывать в куки под названием KEKARDSECURITY")
    public ResponseEntity<String> login(@RequestBody UserDto loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация", description = "Возвращает JWT, который нужно прикладывать в куки под названием KEKARDSECURITY")
    public ResponseEntity<String> register(@RequestBody UserDto userRequest) {
        return ResponseEntity.ok(userService.register(userRequest));
    }
}
