package com.example.girlbe.assembler;

import com.example.girlbe.dto.request.ExperimentCreateRequest;
import com.example.girlbe.dto.response.ExperimentResponse;
import com.example.girlbe.model.Experiment;
import com.example.girlbe.model.ExperimentJSON;
import com.example.girlbe.model.User;
import com.example.girlbe.model.enums.ExperimentState;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExperimentAssembler {

    /*
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
                .generateGraph(experimentCreateRequest.getGenerateGraph())
                .optimisation(experimentCreateRequest.getOptimisation())
                .date(new Date())
                .user(user)
                .state(ExperimentState.PENDING)
                .build();

    }

     */

    public static List<Experiment> createExperimentsFromCSV(ExperimentCreateRequest experimentCreateRequest, User user){
        List<Experiment> experiments = new ArrayList<>();
        try{
            CSVReader reader = new CSVReaderBuilder(new FileReader("src/main/python/config.json.csv"))
                    .withSkipLines(1)
                    .withCSVParser(new CSVParserBuilder().withSeparator('\t').build())
                    .build();
            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {
                Experiment experiment = Experiment.builder()
                        .name(experimentCreateRequest.getName())
                        .graph(line[0])
                        .model(line[1])
                        .instances(line[2])
                        .epsilon(line[3])
                        .decay(line[4])
                        .alpha(line[5])
                        .discount(line[6])
                        .runs(line[7])
                        .time(line[8])
                        .percentage(line[9])
                        .qSize(line[10])
                        .epochs(experimentCreateRequest.getEpochs())
                        .limit(experimentCreateRequest.getLimit())
                        .generateGraph(experimentCreateRequest.getGenerateGraph())
                        .optimisation(experimentCreateRequest.getOptimisation())
                        .png(new File(line[0] + ".png"))
                        .date(new Date())
                        .user(user)
                        .state(ExperimentState.COMPLETED)
                        .build();
                experiments.add(experiment);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return experiments;
    }

    public static ExperimentResponse createExperimentResponse(Experiment experiment) {
        ExperimentResponse experimentResponse = ExperimentResponse.builder()
                .id(experiment.getId())
                .name(experiment.getName())
                .date(experiment.getDate().toString())
                .state(experiment.getState().name())
                .model(experiment.getModel())
                .instances(experiment.getInstances())
                .epsilon(experiment.getEpsilon())
                .decay(experiment.getDecay())
                .alpha(experiment.getAlpha())
                .discount(experiment.getDiscount())
                .runs(experiment.getRuns())
                .epochs(experiment.getEpochs())
                .limit(experiment.getLimit())
                .optimisation(experiment.getOptimisation())
                .generateGraph(experiment.getGenerateGraph())
                .time(experiment.getTime())
                .qSize(experiment.getQSize())
                .percentage(experiment.getPercentage())
                .png("")
                .build();
        try {
            if(experiment.getGraph() != null)
                experimentResponse.setPng(Base64.getEncoder().encodeToString(Files.readAllBytes(Path.of("src/main/python/config/" + experiment.getGraph() + ".png"))));
            else experimentResponse.setPng(experimentResponse.getPng());
        } catch (Exception e){
            e.printStackTrace();
        }
        return experimentResponse;
    }

    public static List<ExperimentResponse> createExperimentResponseList (List<Experiment> experimentList){
        List<ExperimentResponse> experimentResponseList = new ArrayList<>();
        for(Experiment experiment : experimentList){
            experimentResponseList.add(createExperimentResponse(experiment));
        }
        return experimentResponseList;
    }

    public static ExperimentJSON createExperimentJSON(ExperimentCreateRequest experimentCreateRequest){
        ExperimentJSON experimentJSON = ExperimentJSON.builder()
                .generateGraph(Boolean.parseBoolean(experimentCreateRequest.getGenerateGraph()))
                .epsilon(parseStringToDoubleList(experimentCreateRequest.getEpsilon()))
                .decay(parseStringToDoubleList(experimentCreateRequest.getDecay()))
                .alpha(parseStringToDoubleList(experimentCreateRequest.getAlpha()))
                .discount(parseStringToDoubleList(experimentCreateRequest.getDiscount()))
                .epochs(Integer.parseInt(experimentCreateRequest.getEpochs()))
                .optimisation(Integer.parseInt(experimentCreateRequest.getOptimisation()))
                .runs(Integer.parseInt(experimentCreateRequest.getRuns()))
                .model(parseStringToIntegerList(experimentCreateRequest.getModel()))
                .limit(Integer.parseInt(experimentCreateRequest.getLimit()))
                .instances(parseStringToIntegerListList(experimentCreateRequest.getInstances()))
                .build();
        return experimentJSON;
    }

    private static List<Double> parseStringToDoubleList(String inputString) {
        List<Double> doublesList = new ArrayList<>();
        String[] parts = inputString.substring(1, inputString.length() - 1).split(", ");
        for (String part : parts) {
            doublesList.add(Double.parseDouble(part));
        }
        return doublesList;
    }

    private static List<Integer> parseStringToIntegerList(String inputString) {
        List<Integer> integersList = new ArrayList<>();
        String[] parts = inputString.substring(1, inputString.length() - 1).split(", ");
        for (String part : parts) {
            integersList.add(Integer.parseInt(part));
        }
        return integersList;
    }

    private static List<List<Integer>> parseStringToIntegerListList(String inputString) {
        List<List<Integer>> integersListList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(\\d+(?:,\\s*\\d+)*)\\]");
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()) {
            String subList = matcher.group(1);
            List<Integer> integersList = new ArrayList<>();
            String[] parts = subList.split(",");
            for (String part : parts) {
                integersList.add(Integer.parseInt(part.trim()));
            }
            integersListList.add(integersList);
        }
        return integersListList;
    }
}
