package com.noboruu.digica.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "card_set", schema = "public")
public class CardSet implements Serializable {

    @Serial
    private static final long serialVersionUID = -4387917708957517100L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('card_set_id_seq')")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
    private String code;

    @CreationTimestamp
    @Column(name = "created_ts", nullable = false, updatable = false)
    private Date createdTs;

    @UpdateTimestamp
    @Column(name = "updated_ts", nullable = false)
    private Date updatedTs;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cardSet")
    @OrderBy("id")
    private List<Card> cards = new ArrayList<>();

}