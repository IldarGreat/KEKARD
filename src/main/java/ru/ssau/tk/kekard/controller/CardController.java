package ru.ssau.tk.kekard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ssau.tk.kekard.entity.CardDto;
import ru.ssau.tk.kekard.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
@PreAuthorize("hasAnyAuthority('USER')")
public class CardController {
    private final CardService cardService;

    @PostMapping(produces = "application/json; charset=UTF-8")
    @Operation(summary = "Создает карточку", description = "Нужен JWT в куках под названием KEKARDSECURITY")
    public ResponseEntity<Void> createCard(@RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.createCard(cardDto));
    }

    @GetMapping(value = "/{card_id}", produces = "application/json; charset=UTF-8")
    @Operation(summary = "Возвращает карточку по ID", description = "Нужен JWT в куках под названием KEKARDSECURITY")
    public ResponseEntity<CardDto> getCardById(@PathVariable("card_id") Integer cardId) {
        return ResponseEntity.ok(cardService.getCardById(cardId));
    }

    @GetMapping(value = "/category/{category}", produces = "application/json; charset=UTF-8")
    @Operation(summary = "Возвращает карточки по названию категории", description = "Нужен JWT в куках под названием KEKARDSECURITY")
    public ResponseEntity<List<CardDto>> getCardsByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok(cardService.getCardsByCategory(category));
    }

    @PatchMapping(value = "/{card_id}", produces = "application/json; charset=UTF-8")
    @Operation(summary = "Изменяет карточку", description = "Нужен JWT в куках под названием KEKARDSECURITY")
    public ResponseEntity<CardDto> patchCard(@PathVariable("card_id") Integer cardId, @RequestBody CardDto cardDto) {
        return ResponseEntity.ok(cardService.patchCardById(cardDto, cardId));
    }
}
