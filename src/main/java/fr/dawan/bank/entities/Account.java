package fr.dawan.bank.entities;

import fr.dawan.bank.exceptions.InsufficientFundsException;
import lombok.*;

import java.util.List;

//@Entity
@AllArgsConstructor // Générer le constructeur avec tous les arguments
@NoArgsConstructor // Générer le constructeur vide
@Getter // Générer les getters
@Setter // Générer les setters
public class Account {
    private int id;
    private double solde;
    private String code;
    private List<String> history;

    public void deposit(double amount) {
        solde += amount;
        history.add("Deposit : "+amount);
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if(solde < amount) throw new InsufficientFundsException(solde,amount);
        solde -= amount;
        history.add("Withdraw : "+amount);
    }
}
