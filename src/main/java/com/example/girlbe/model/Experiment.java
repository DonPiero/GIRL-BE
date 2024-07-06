package com.example.girlbe.model;

import com.example.girlbe.model.enums.ExperimentState;
import jakarta.persistence.*;
import lombok.*;

import java.io.File;
import java.time.LocalDate;

@Entity
@Table(name="Experiments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Experiment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "date")
    @NonNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "experiment_state")
    @NonNull
    private ExperimentState state;

    @Column(name = "model")
    private String model;

    @Column(name = "instance")
    private String instance;

    @Column(name = "epsilon")
    private String epsilon;

    @Column(name = "decay")
    private String decay;

    @Column(name = "alpha")
    private String alpha;

    @Column(name = "discount")
    private String discount;

    @Column(name = "runs")
    private String run;

    @Column(name = "epochs")
    private String epochs;

    @Column(name = "experiment_limit")
    private String limit;

    @Column(name = "generate_graph")
    private String generateGraph;

    @Column(name = "optimisation")
    private String optimisation;

    @Column(name = "graph")
    private String graph;

    @Column(name = "time")
    private String time;

    @Column(name = "qSize")
    private String qSize;

    @Column(name = "percentage")
    private String percentage;

    @Column(name = "png")
    private File png;

    public void setCompleted(){
        this.state = ExperimentState.COMPLETED;
        this.user.completeExperiment();
    }

}
