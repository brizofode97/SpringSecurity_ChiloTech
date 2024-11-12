package com.chilo_tech.demo.common.utility;

import jakarta.persistence.*;

public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long identifiant;

}
