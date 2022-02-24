package uk.co.nyvil.bot.commands.commands;

import net.dv8tion.jda.api.Permission;
import uk.co.nyvil.bot.BotConnection;
import uk.co.nyvil.bot.commands.manager.*;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;
import uk.co.nyvil.utils.MessageUtils;

public class PingCommand implements SlashCommand {

    @Override
    @SlashCommandAnnotation(name = "ping")
    public void execute(SlashCommandExecutionInfo info) {
        final long time = System.currentTimeMillis();
        info.getEvent().replyEmbeds(MessageUtils.createInfoEmbed("Current Gateaway latency: " + BotConnection.getJda().getGatewayPing() + "ms\nResponse time: measuring...").build()).queue(message -> {
            message.editOriginalEmbeds(MessageUtils.createInfoEmbed("Current Gateaway latency: " + BotConnection.getJda().getGatewayPing() + "ms\nResponse time: " + (System.currentTimeMillis() - time) + "ms").build()).queue();
        });
    }
}
