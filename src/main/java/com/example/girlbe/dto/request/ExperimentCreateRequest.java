package com.example.girlbe.dto.request;

import lombok.Getter;

@Getter
public class ExperimentCreateRequest {
    private String name;
    private Integer model;
    private String instances;
    private Integer epsilon;
    private Integer decay;
    private Integer alpha;
    private Integer discount;
    private Integer runs;
    private Integer epochs;
    private Integer limit;
    private Boolean generate_graph;
}
