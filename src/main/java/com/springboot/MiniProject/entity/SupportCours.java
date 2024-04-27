package com.springboot.MiniProject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;

@Getter
@Setter
@Data
@Entity
@Table(name = "Support_Cours")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupportCours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelleSupport;
    @Lob
    @Column(name = "fichier_pdf")
    @JdbcTypeCode(Types.VARBINARY)
    private byte[] fichier;
    @ManyToOne
    @JoinColumn(name = "id_matiere")
    private Matiere matiere;

}
