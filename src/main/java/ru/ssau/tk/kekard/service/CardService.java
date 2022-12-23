package ru.ssau.tk.kekard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
import ru.ssau.tk.kekard.entity.Card;
import ru.ssau.tk.kekard.entity.CardDto;
import ru.ssau.tk.kekard.entity.User;
import ru.ssau.tk.kekard.repository.CardRepository;
import ru.ssau.tk.kekard.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public Void createCard(CardDto cardDto) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(login);
        if (cardDto.key().equals("") || cardDto.value().equals("") || cardDto.category().equals("")) {
            throw new ServerErrorException("");
        }
        Card card = Card.builder()
                .key(cardDto.key())
                .value(cardDto.value())
                .category(cardDto.category())
                .user(user)
                .build();
        cardRepository.save(card);
        return null;
    }

    public CardDto getCardById(Integer id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
        return new CardDto(card.getKey(), card.getValue(), card.getCategory());
    }

    public List<CardDto> getCardsByCategory(String category) {
        List<Card> card = cardRepository.findAllByCategory(category);
        List<CardDto> cardDtos = new ArrayList<>();
        for (Card card1 : card) {
            cardDtos.add(CardDto
                    .builder()
                    .key(card1.getKey())
                    .value(card1.getValue())
                    .category(card1.getCategory())
                    .build());
        }
        return cardDtos;
    }

    public CardDto patchCardById(CardDto cardDto, Integer id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByLogin(login);
        Card card = cardRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
        if (card.getUser() != user) {
            throw new BadCredentialsException("");
        }
        String key = cardDto.key();
        String value = cardDto.value();
        String category = cardDto.category();
        if (!key.isEmpty()) {
            card.setKey(key);
        }
        if (!value.isEmpty()) {
            card.setValue(value);
        }
        if (!category.isEmpty()) {
            card.setCategory(category);
        }
        cardRepository.save(card);
        return CardDto.builder()
                .key(card.getKey())
                .value(card.getValue())
                .category(card.getCategory())
                .build();
    }
}
