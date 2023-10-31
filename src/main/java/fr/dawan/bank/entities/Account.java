package fr.dawan.bank.entities;

import fr.dawan.bank.exceptions.InsufficientFundsException;
import fr.dawan.bank.tools.RandomTool;
import lombok.*;

import java.util.ArrayList;
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
    private List<String> history = new ArrayList<>();

    public double deposit(double amount) {
        solde += amount;
        addHistory("Deposit", amount);
        return solde;
    }

    public double withdraw(double amount) throws InsufficientFundsException {
        if(solde < amount) throw new InsufficientFundsException(solde,amount);
        solde -= amount;
        addHistory("Withdraw", amount);
        return solde;
    }

    public void transfer(double amount, Account target) throws InsufficientFundsException {
        withdraw(amount);
        target.deposit(amount);
    }

    private void addHistory(String action, double amount) {
        history.add("%s : %.2f €".formatted(action,amount)); // "".formated(...) équivalent à String.format("",...)
    }

    public String generateCode() {
        code = RandomTool.generateCode(4);
        return code;
    }
}
