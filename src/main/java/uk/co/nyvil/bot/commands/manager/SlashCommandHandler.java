package uk.co.nyvil.bot.commands.manager;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import uk.co.nyvil.Bot;
import uk.co.nyvil.bot.BotConnection;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;
import uk.co.nyvil.utils.MessageUtils;
import uk.co.nyvil.utils.PermissionUtils;

import java.nio.channels.Channel;
import java.util.Random;

public class SlashCommandHandler extends ListenerAdapter {

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if(event.getGuild() == null) return;
        if(event.getUser().isBot()) return;
        if(event.getMember() == null) return;

        Bot.getInstance().getCommandHandler().getSlashCommandMap().forEach((commandAnnotation, command) -> {
            if(commandAnnotation.name().equalsIgnoreCase(event.getName()) && event.getMember().hasPermission(command.neededPermission())) {
                SlashCommandExecutionInfo info = new SlashCommandExecutionInfo(command, event, event.getMember(), event.getTextChannel(), commandAnnotation, event.getOptions());
                command.execute(info);
            }
        });

    }
}
