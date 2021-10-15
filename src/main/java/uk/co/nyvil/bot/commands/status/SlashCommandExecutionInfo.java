package uk.co.nyvil.bot.commands.status;

/**
 * Created by Nyvil on 11/06/2021 at 21:30
 * in project Discord Base
 */

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import uk.co.nyvil.bot.commands.manager.SlashCommand;
import uk.co.nyvil.bot.commands.manager.SlashCommandAnnotation;

import java.util.List;

@Getter
@Setter
public class SlashCommandExecutionInfo {

    private final SlashCommand command;
    private final SlashCommandEvent event;
    private final Member sender;
    private final TextChannel textChannel;
    private final SlashCommandAnnotation annotation;
    private final List<OptionMapping> options;

    public SlashCommandExecutionInfo(SlashCommand command, SlashCommandEvent event, Member sender, TextChannel textChannel, SlashCommandAnnotation annotation, List<OptionMapping> options) {
        this.command = command;
        this.event = event;
        this.sender = sender;
        this.textChannel = textChannel;
        this.annotation = annotation;
        this.options = options;
    }
}