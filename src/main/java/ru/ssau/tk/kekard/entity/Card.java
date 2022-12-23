package ru.ssau.tk.kekard.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "key")
    private String key;
    @Column(name = "value")
    private String value;
    @Column(name = "category")
    private String category;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
