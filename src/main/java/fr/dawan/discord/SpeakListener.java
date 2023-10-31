package fr.dawan.discord;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.Objects;

public class SpeakListener extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild serveur = event.getGuild();
        SlashCommandData speakCommand = Commands.slash("speak", "Faire Parler son bot");
        OptionData text = new OptionData(OptionType.STRING, "text", "Le texte à afficher", true);
        speakCommand.addOptions(text);
        serveur.updateCommands().addCommands(speakCommand).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
            String commandName = event.getName();
            if(commandName.equals("speak")) {
                // Le retour de getOption peur être null, c'est pour cela qu'on a un avertissement
                // event.getOption("text").getAsString();
                /*OptionMapping option = event.getOption("text");
                String optionAsString = option != null ? option.getAsString() : "Hello Humans !";
                event.reply(optionAsString).queue();*/
                event.reply(Objects.requireNonNull(event.getOption("text")).getAsString()).queue();
            }
    }
}
