package com.oh.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="target")
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TARGET_ID")
    private Integer targetId;
    @Column(name = "FINAL_TARGET")
    private String finalTarget;
    @Column(name = "FINAL_REWARD")
    private String finalReward;
    @Column(name = "FIRST_STEP_TARGET")
    private String firstStepTarget;
    @Column(name = "SECOND_STEP_TARGET")
    private String secondStepTarget;
    @Column(name = "THIRD_STEP_TARGET")
    private String thirdStepTarget;
    @Column(name = "CURRENT_DAYS")
    private Integer currentDays;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User creator;







}
