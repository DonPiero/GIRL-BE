package com.example.girlbe.controller;

import com.example.girlbe.assembler.ExperimentAssembler;
import com.example.girlbe.dto.request.ExperimentCreateRequest;
import com.example.girlbe.dto.response.ExperimentResponse;
import com.example.girlbe.service.ExperimentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/experiments")
@CrossOrigin(origins = "http://localhost:4200")
public class ExperimentController {
    private final ExperimentService experimentService;

    @GetMapping(path = "/")
    public List<ExperimentResponse> getAllExperiments() {
        Long id = 11L;
        return ExperimentAssembler.createExperimentResponseList(experimentService.getExperimentsByUserId(id));
    }

    @GetMapping(path = "/{id}")
    public ExperimentResponse getExperimentById(@PathVariable Long id) {
        return ExperimentAssembler.createExperimentResponse(experimentService.getExperimentById(id));
    }

    @PostMapping(path = "/create")
    public ExperimentResponse createExperiment(@RequestBody ExperimentCreateRequest experimentCreateRequest) {
        Long id = 11L;
        return ExperimentAssembler.createExperimentResponse(experimentService.createExperiment(experimentCreateRequest, id));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteExperimentById(@PathVariable Long id) {
        experimentService.deleteExperimentById(id);
    }
}
