package com.example.girlbe.dto.request;

import lombok.Getter;

@Getter
public class ExperimentCreateRequest {
    private String name;
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
}
