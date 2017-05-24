package com.codecool.repository;

import com.codecool.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Integer> {
    boolean existsByAdUrl(String url);

    List<Flat> findAllByOrderByDate();
}
