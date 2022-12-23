package ru.ssau.tk.kekard.entity;

import lombok.Builder;

@Builder
public record CardDto(String key,
                      String value,
                      String category) {
}
