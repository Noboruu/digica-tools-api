package com.noboruu.digica.repository;

import com.noboruu.digica.model.entity.CardSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardSetRepository extends JpaRepository<CardSet, Long> {

    List<CardSet> findAllByOrderByIdAsc();
}
