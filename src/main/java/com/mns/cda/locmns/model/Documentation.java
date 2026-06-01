package com.mns.cda.locmns.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Documentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;


    @Column(columnDefinition = "TEXT")
    @Length(max = 1500)
    protected String description;

    @ManyToOne
    @JoinColumn(name = "materiel_id") // FK en base
    private Materiel materiel;
}
