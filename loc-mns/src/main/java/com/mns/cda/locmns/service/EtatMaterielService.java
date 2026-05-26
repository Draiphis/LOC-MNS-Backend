package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.DocumentationDao;
import com.mns.cda.locmns.dto.CreateDocumentationDto;
import com.mns.cda.locmns.dto.UpdateDocumentationDto;
import com.mns.cda.locmns.model.Documentation;
import com.mns.cda.locmns.model.Etat;
import com.mns.cda.locmns.model.EtatMateriel;
import com.mns.cda.locmns.model.Materiel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class EtatMaterielService {

    public Etat getEtatActuel(Materiel materiel) {

        return materiel.getEtat()
                .stream()
                .max(Comparator.comparing(
                        EtatMateriel::getDateModificationEtat
                ))
                .map(EtatMateriel::getEtat)
                .orElse(null);
    }
}
