package com.example.girlbe.model;

import com.example.girlbe.model.enums.ExperimentState;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "experiment_state")
    @NonNull
    private ExperimentState state;

    @Column(name = "model")
    private Integer model;

    @Column(name = "instances")
    private String instances;

    @Column(name = "epsilon")
    private Integer epsilon;

    @Column(name = "decay")
    private Integer decay;

    @Column(name = "alpha")
    private Integer alpha;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "runs")
    private Integer runs;

    @Column(name = "epochs")
    private Integer epochs;

    @Column(name = "experiment_limit")
    private Integer limit;

    @Column(name = "generate_graph")
    private Boolean generate_graph;
}
