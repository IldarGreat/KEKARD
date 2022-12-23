package ru.ssau.tk.kekard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.tk.kekard.entity.Card;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,Integer> {
    List<Card> findAllByCategory(String category);
}
