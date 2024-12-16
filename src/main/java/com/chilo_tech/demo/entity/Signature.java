package com.chilo_tech.demo.entity;

import com.chilo_tech.demo.common.utility.StatusSignature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "signature")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusSignature type;

    @Column(name = "url_fichier_signer")
    private String url;

}
