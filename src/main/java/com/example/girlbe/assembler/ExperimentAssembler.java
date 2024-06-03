package com.example.girlbe.assembler;

import com.example.girlbe.dto.request.ExperimentCreateRequest;
import com.example.girlbe.dto.response.ExperimentResponse;
import com.example.girlbe.model.Experiment;
import com.example.girlbe.model.User;
import com.example.girlbe.model.enums.ExperimentState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExperimentAssembler {

    public static Experiment createExperiment(ExperimentCreateRequest experimentCreateRequest, User user){
        return Experiment.builder()
                .name(experimentCreateRequest.getName())
                .model(experimentCreateRequest.getModel())
                .instances(experimentCreateRequest.getInstances())
                .epsilon(experimentCreateRequest.getEpsilon())
                .decay(experimentCreateRequest.getDecay())
                .alpha(experimentCreateRequest.getAlpha())
                .discount(experimentCreateRequest.getDiscount())
                .runs(experimentCreateRequest.getRuns())
                .epochs(experimentCreateRequest.getEpochs())
                .limit(experimentCreateRequest.getLimit())
                .generate_graph(experimentCreateRequest.getGenerate_graph())
                .date(new Date())
                .user(user)
                .state(ExperimentState.PENDING)
                .build();

    }

    public static ExperimentResponse createExperimentResponse(Experiment experiment){
        return ExperimentResponse.builder()
                .id(experiment.getId())
                .name(experiment.getName())
                .date(experiment.getDate().toString())
                .state(ExperimentState.PENDING.name())
                .model(experiment.getModel())
                .instances(experiment.getInstances())
                .epsilon(experiment.getEpsilon())
                .decay(experiment.getDecay())
                .alpha(experiment.getAlpha())
                .discount(experiment.getDiscount())
                .runs(experiment.getRuns())
                .epochs(experiment.getEpochs())
                .limit(experiment.getLimit())
                .generate_graph(experiment.getGenerate_graph())
                .build();
    }

    public static List<ExperimentResponse> createExperimentResponseList (List<Experiment> experimentList){
        List<ExperimentResponse> experimentResponseList = new ArrayList<>();
        for(Experiment experiment : experimentList){
            experimentResponseList.add(createExperimentResponse(experiment));
        }
        return experimentResponseList;
    }
}
