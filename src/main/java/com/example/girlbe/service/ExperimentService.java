package com.example.girlbe.service;

import com.example.girlbe.assembler.ExperimentAssembler;
import com.example.girlbe.dto.request.ExperimentCreateRequest;
import com.example.girlbe.model.Experiment;
import com.example.girlbe.model.User;
import com.example.girlbe.model.enums.ExperimentState;
import com.example.girlbe.repository.ExperimentRepository;
import com.example.girlbe.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
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

    public List<Experiment> createExperiment(ExperimentCreateRequest experimentCreateRequest, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        runScript(experimentCreateRequest);
        List<Experiment> experiments = ExperimentAssembler.createExperimentsFromCSV(experimentCreateRequest, user);
        experimentRepository.saveAll(experiments);
        return experiments;
    }

    public void deleteExperimentById(Long id) {
        experimentRepository.deleteById(id);
    }

    private void runScript(ExperimentCreateRequest experimentCreateRequest) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/python/config.json");
        try {
            mapper.writeValue(file, ExperimentAssembler.createExperimentJSON(experimentCreateRequest));
            System.out.println("JSON file created successfully: " + file.getPath());

            Process process = Runtime.getRuntime().exec("python ./src/main/python/main8-puzzle.py ./src/main/python/config.json");
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Python script executed successfully.");
            } else {
                System.out.println("Python script execution failed with exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
