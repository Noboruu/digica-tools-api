package com.noboruu.digica.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "card", schema = "public")
public class Card implements Serializable {

    @Serial
    private static final long serialVersionUID = -3988961723196428004L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
    private String code;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "art_url", length = Integer.MAX_VALUE)
    private String artUrl;

    @Column(name = "card_type", length = Integer.MAX_VALUE)
    private String cardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_set_id", nullable = false)
    private CardSet cardSet;

    @CreationTimestamp
    @Column(name = "created_ts", nullable = false, updatable = false)
    private Date createdTs;

    @UpdateTimestamp
    @Column(name = "updated_ts", nullable = false)
    private Date updatedTs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "card")
    @OrderBy("id")
    private List<CardEffect> cardEffects = new ArrayList<>();

}