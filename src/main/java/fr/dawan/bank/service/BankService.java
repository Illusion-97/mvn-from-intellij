package fr.dawan.bank.service;

import fr.dawan.bank.entities.Account;
import fr.dawan.bank.exceptions.AccountNotFoundException;
import fr.dawan.bank.exceptions.InsufficientFundsException;
import fr.dawan.bank.mappers.AccountMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BankService {
    private static final Map<String, Account> accounts = new HashMap<>();
    private static final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    public static void createAccount(String name) {
        accounts.put(name, new Account());
    }

    //@Nullable Permet de préciser qu'il y a des situations ou le retour peut être null
    private static Account getAccount(@NotNull String name) // Permet d'avertir que cette méthode préfère ne pas recevoir de valeur nulle
            throws AccountNotFoundException {
        if(!accounts.containsKey(name)) throw new AccountNotFoundException(name);
        return accounts.get(name);
    }

    public static String getAccountData(String name) throws AccountNotFoundException {
        //Objects.toString prévient les erreurs en cas de valeur nulle à mettre au format String en donnant la possibilité de définir une valeur par défaut
        // Garder le réflexe de remplacer 'value.toString()' par Objects.toString(value,"default")
        return Objects.toString(accountMapper.toDto(getAccount(name)), "Erreur durant la récupération du compte");
    }

    public static double deposit(String name, double amount) throws AccountNotFoundException {
        return getAccount(name).deposit(amount);
    }
    public static double withdraw(String name, double amount) throws AccountNotFoundException, InsufficientFundsException {
        return getAccount(name).withdraw(amount);
    }
    public static void transfer(String name, String targetName, double amount) throws AccountNotFoundException, InsufficientFundsException {
        getAccount(name).transfer(amount,getAccount(targetName));
    }

    public static String generateCode(String name) throws AccountNotFoundException {
        return getAccount(name).generateCode();
    }
}
