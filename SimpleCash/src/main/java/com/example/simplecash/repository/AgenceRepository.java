package com.example.simplecash.repository;

import com.example.simplecash.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agence, String> {
}

