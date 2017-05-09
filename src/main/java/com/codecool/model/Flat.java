package com.codecool.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Data
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String address;

    @NotNull
    private float squareMeter;

    @NotNull
    private String district;

    @NotNull
    private int rent;

    @NotNull
    private String company;

    @Column(unique = true)
    @NotNull
    private String adUrl;

}