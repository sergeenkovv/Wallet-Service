package com.ivan.walletservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Player entity.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "develop")
public class Player {

    /**
     * The unique identifier for the player.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The login username of the player.
     */
    private String login;

    /**
     * The password of the player.
     */
    private String password;

    /**
     * The balance of the player. Default value is 0.
     */
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * The list of transactions associated with the player.
     */
    @Builder.Default
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactionList = new ArrayList<>();
}