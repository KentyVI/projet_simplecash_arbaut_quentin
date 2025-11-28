package com.example.simplecash.repository;

import com.example.simplecash.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}

