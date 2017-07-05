package com.codecool.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@ToString(exclude = {"password"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "myUser_flat",
            joinColumns = @JoinColumn(name = "myUser_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "flat_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"myUser_id", "flat_id"}))
    private List<Flat> flats;
}
