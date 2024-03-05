package com.artcorb.cards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.artcorb.cards.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

}
