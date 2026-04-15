package com.mns.cda.locmns.model;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EtatMateriel {

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Key implements Serializable {
        @Column(name = "etat_id")
        Integer etatId;
        @Column(name = "materiel_id")
        Integer materielId;
    }


    @EmbeddedId
    private Key id;

    @ManyToOne
    @MapsId("etatId")
    @JoinColumn(name = "etat_id")
    protected Etat etat;

    @ManyToOne
    @MapsId("materielId")
    @JoinColumn(name = "materiel_id")
    protected Materiel materiel;


    protected LocalDateTime dateModificationEtat;
}
