package com.example.simplecash.repository;

import com.example.simplecash.entity.Banque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanqueRepository extends JpaRepository<Banque, Long> {
}

