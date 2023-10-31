package fr.dawan.bank.exceptions;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(double funds, double asked) {
        super(String.format("Impossible de retirer %.2f€, Solde actuel : %.2f€", asked,funds));
    }
}
