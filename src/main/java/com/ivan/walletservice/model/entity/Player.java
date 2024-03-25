package com.ivan.walletservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "develop")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
//    @Column(unique = true)
    private String login;

//    @NotNull
    private String password;

    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Builder.Default
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactionList = new ArrayList<>();
}