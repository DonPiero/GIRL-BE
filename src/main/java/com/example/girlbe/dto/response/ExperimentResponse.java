package com.example.girlbe.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ExperimentResponse {
    private Long id;
    private String name;
    private String date;
    private String state;
    private String model;
    private String instances;
    private String epsilon;
    private String decay;
    private String alpha;
    private String discount;
    private String runs;
    private String epochs;
    private String limit;
    private String generateGraph;
    private String optimisation;
    private String time;
    private String qSize;
    private String percentage;
    private String png;
}
