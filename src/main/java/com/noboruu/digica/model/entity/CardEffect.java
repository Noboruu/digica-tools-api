package com.noboruu.digica.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

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
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @CreationTimestamp
    @Column(name = "created_ts", nullable = false, updatable = false)
    private Date createdTs;

    @UpdateTimestamp
    @Column(name = "updated_ts", nullable = false)
    private Date updatedTs;

}