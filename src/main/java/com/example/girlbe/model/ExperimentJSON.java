package com.example.girlbe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ExperimentJSON {
    private List<Double> epsilon;
    private List<Double> decay;
    private List<Double> alpha;
    private List<Double> discount;
    @JsonProperty("no_of_epochs")
    private Integer epochs;
    private Integer limit;
    private Integer runs;
    private List<Integer> model;
    private List<List<Integer>> instances;
    @JsonProperty("generate-graph")
    private Boolean generateGraph;
    private Integer optimisation;

}
