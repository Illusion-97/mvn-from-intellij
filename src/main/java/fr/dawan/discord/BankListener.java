package fr.dawan.discord;

import fr.dawan.bank.service.BankService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.Objects;

public class BankListener extends ListenerAdapter {
    public enum BankCommand implements Command{
        CHECK, DEPOSIT, WITHDRAW, TRANSFER, GENERATE;

        @Override
        public String getActionName() {
            return name().toLowerCase();
        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild serveur = event.getGuild();
        List<Member> members = serveur.getMembers();
        members.stream()
                .filter(member -> !member.getUser().isBot())
//                .forEach(member -> BankService.createAccount(member.getEffectiveName()));
                .map(Member::getEffectiveName)
                .forEach(BankService::createAccount);
        //OptionData code = new OptionData(OptionType.INTEGER, "code", "Le code du compte", true);
        serveur.updateCommands().addCommands(
                Commands.slash(BankCommand.CHECK.getActionName(), "Consultation de compte"),
                Commands.slash(BankCommand.GENERATE.name().toLowerCase(), "Génération du code du compte"),
                Commands.slash(BankCommand.DEPOSIT.getActionName(), "Déposer une somme sur le compte")
                        .addOptions(getAmountOption("déposer")),
                Commands.slash(BankCommand.WITHDRAW.getActionName(), "Retrait d'une somme sur le compte")
                        .addOptions(getAmountOption("retirer")),
                Commands.slash(BankCommand.TRANSFER.getActionName(), "Transférer une somme vers le compte d'un autre membre")
                        .addOptions(getAmountOption("transférer"))
                        .addOption(OptionType.USER, "user", "Bénéficiaire du montant", true)
        ).queue();
    }

    private OptionData getAmountOption(String action) {
        return new OptionData(OptionType.NUMBER, "amount", "Montant à " + action, true);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String response;
        boolean ephemeral = false;
        try {
            BankCommand action = BankCommand.valueOf(event.getName().toUpperCase());
            String name = Objects.requireNonNull(event.getMember()).getEffectiveName();
            switch (action) {
                case CHECK ->  response = BankService.getAccountData(name);
                case DEPOSIT -> {
                    double amount = Objects.requireNonNull(event.getOption("amount")).getAsDouble();
                    response = "Dépot de %.2f réussi, nouveau solde : %.2f €".formatted(amount,BankService.deposit(name,amount));
                }
                case WITHDRAW -> {
                    double amount = Objects.requireNonNull(event.getOption("amount")).getAsDouble();
                    response = "Retrait de %.2f réussi, nouveau solde : %.2f €".formatted(amount,BankService.withdraw(name,amount));
                }
                case TRANSFER -> {
                    double amount = Objects.requireNonNull(event.getOption("amount")).getAsDouble();
                    String targetName =  Objects.requireNonNull(Objects.requireNonNull(event.getOption("user")).getAsMember()).getEffectiveName();
                    response = "Transfert de %.2f réussi vers le compte de %s".formatted(
                            amount,targetName);
                }
                case GENERATE -> response = "Code Généré : %s".formatted(BankService.generateCode(name));
                default -> throw new IllegalStateException("I'm a TeaPot !");
            }
        } catch (Exception e) {
            response = e.getMessage();
            ephemeral = true;
        }
        event.reply(response).setEphemeral(ephemeral).queue();
    }
}
