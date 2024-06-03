package com.example.girlbe.service;

import com.example.girlbe.assembler.ExperimentAssembler;
import com.example.girlbe.dto.request.ExperimentCreateRequest;
import com.example.girlbe.model.Experiment;
import com.example.girlbe.model.User;
import com.example.girlbe.repository.ExperimentRepository;
import com.example.girlbe.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class ExperimentService {
    private final ExperimentRepository experimentRepository;
    private final UserRepository userRepository;


    public List<Experiment> getExperimentsByUserId(Long id) {
        return experimentRepository.findAllByUserId(id);
    }

    public Experiment getExperimentById(Long id) {
        return experimentRepository.findExperimentById(id).orElse(null);
    }

    public Experiment createExperiment(ExperimentCreateRequest experimentCreateRequest, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return experimentRepository.save(ExperimentAssembler.createExperiment(experimentCreateRequest, user));
    }

    public void deleteExperimentById(Long id) {
        experimentRepository.deleteById(id);
    }
}
