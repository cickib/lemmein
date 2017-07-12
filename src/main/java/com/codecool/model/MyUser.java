package com.codecool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@ToString(exclude = {"password"})
@Getter
@Setter
@NoArgsConstructor
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String userName;
    private String password;
    private Date regDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "myUser_flat",
            joinColumns = @JoinColumn(name = "myUser_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "flat_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"myUser_id", "flat_id"}))
    private List<Flat> flats;

    public MyUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
