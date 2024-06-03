package com.example.girlbe.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExperimentResponse {
    private Long id;
    private String name;
    private String date;
    private String state;
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
