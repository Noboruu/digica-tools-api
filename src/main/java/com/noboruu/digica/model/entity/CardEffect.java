package com.noboruu.digica.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "card_effect", schema = "public")
public class CardEffect implements Serializable {

    @Serial
    private static final long serialVersionUID = -1633274413499088604L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "effect_type", nullable = false, length = Integer.MAX_VALUE)
    private String effectType;

    @Column(name = "effect_text", nullable = false, length = Integer.MAX_VALUE)
    private String effectText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "created_ts")
    private Instant createdTs;

    @Column(name = "updated_ts")
    private Instant updatedTs;

}