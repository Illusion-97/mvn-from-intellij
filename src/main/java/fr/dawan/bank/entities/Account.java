package fr.dawan.bank.entities;

import lombok.*;

import java.util.List;

@AllArgsConstructor // Générer le constructeur avec tous les arguments
@NoArgsConstructor // Générer le constructeur vide
@Getter // Générer les getters
@Setter // Générer les setters
public class Account {
    private int id;
    private double solde;
    private String code;
    private List<String> history;
}
