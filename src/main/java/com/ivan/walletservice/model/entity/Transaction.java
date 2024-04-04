package com.ivan.walletservice.model.entity;

import com.ivan.walletservice.model.type.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * This class represents a Transaction entity.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "develop")
public class Transaction {

    /**
     * The unique identifier for the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of the transaction.
     */
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    /**
     * Transaction value. Default value is 0.
     */
    @Builder.Default
    private BigDecimal amount = BigDecimal.ZERO;

    /**
     * The player associated with this transaction.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;
}