package fr.dawan.discord;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class SpeakListener extends ListenerAdapter {
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild serveur = event.getGuild();
        SlashCommandData speakCommand = Commands.slash("speak", "Faire Parler son bot");
        serveur.updateCommands().addCommands(speakCommand).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
    }
}
