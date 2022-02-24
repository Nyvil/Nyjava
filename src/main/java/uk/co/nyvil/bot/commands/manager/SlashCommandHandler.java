package uk.co.nyvil.bot.commands.manager;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import uk.co.nyvil.Bot;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;
import uk.co.nyvil.utils.MessageUtils;

public class SlashCommandHandler extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if(event.getGuild() == null) return;
        if(event.getUser().isBot()) return;
        if(event.getMember() == null) return;

        Bot.getInstance().getCommandHandler().getSlashCommandMap().forEach((commandAnnotation, command) -> {
            if(commandAnnotation.name().equalsIgnoreCase(event.getName())) {
                if(event.getMember().hasPermission(commandAnnotation.neededPermission())) {
                    SlashCommandExecutionInfo info = new SlashCommandExecutionInfo(command, event, event.getMember(), event.getTextChannel(), commandAnnotation, event.getOptions());
                    command.execute(info);
                } else {
                    event.replyEmbeds(MessageUtils.createErrorEmbed("You don't have permission to execute this command! You're missing the `" + commandAnnotation.neededPermission().getName() + "` permission.").build()).queue();
                }
            }
        });
    }
}
