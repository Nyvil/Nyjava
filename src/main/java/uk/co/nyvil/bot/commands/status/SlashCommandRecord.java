package uk.co.nyvil.bot.commands.status;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import uk.co.nyvil.bot.commands.manager.SlashCommand;

import java.util.List;

public record SlashCommandRecord(SlashCommand slashCommand, SlashCommandInteractionEvent event, Member sender, TextChannel textChannel, List<OptionMapping> options) {

}
