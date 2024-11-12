package com.chilo_tech.demo.entity;

import com.chilo_tech.demo.common.utility.TypeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

//    @Enumerated(EnumType.STRING)
    private String libelle;

}
