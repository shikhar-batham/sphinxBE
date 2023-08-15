package com.crestdevs.sphinxbe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "roles")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private Integer id;
    private String name;
}
