package com.example.girlbe.repository;

import com.example.girlbe.model.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExperimentRepository extends JpaRepository<Experiment, Long> {

    Optional<Experiment> findExperimentById(Long id);
    List<Experiment> findAllByUserId(Long id);
}
