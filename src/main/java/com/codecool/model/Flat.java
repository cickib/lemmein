package com.codecool.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Data
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private int squareMeter;

    @NotNull
    private int rent;

    @NotNull
    private String district;

    private String address;

    @Column(unique = true)
    @NotNull
    private String adUrl;

    @NotNull
    private String company;

    @NotNull
    private Status status = Status.NEW;

    @NotNull
    private Date date = new Date();

    @NotNull
    private boolean interested = false;

}