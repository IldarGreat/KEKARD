package ru.ssau.tk.kekard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.tk.kekard.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByLogin(String login);
}
