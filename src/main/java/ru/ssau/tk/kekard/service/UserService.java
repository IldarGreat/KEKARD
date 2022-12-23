package ru.ssau.tk.kekard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ssau.tk.kekard.entity.User;
import ru.ssau.tk.kekard.entity.UserDto;
import ru.ssau.tk.kekard.repository.UserRepository;
import ru.ssau.tk.kekard.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public String register(UserDto userDto) {
        User user = User.builder()
                .login(userDto.login())
                .password(passwordEncoder.encode(userDto.password()))
                .build();
        userRepository.save(user);
        return jwtTokenProvider.createToken(userDto.login());
    }

    public String login(UserDto userDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.login(), userDto.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return jwtTokenProvider.createToken(userDto.login());
    }
}
