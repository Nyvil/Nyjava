package uk.co.nyvil.bot.commands.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.components.Button;
import uk.co.nyvil.bot.BotConnection;
import uk.co.nyvil.bot.buttons.manager.ButtonAnnotation;
import uk.co.nyvil.bot.buttons.status.ButtonClickInfo;
import uk.co.nyvil.bot.commands.manager.*;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;
import uk.co.nyvil.utils.MessageUtils;

import java.util.Objects;

public class PingCommand implements SlashCommand {

    @Override
    @SlashCommandAnnotation(name = "ping")
    public void execute(SlashCommandExecutionInfo info) {
        final long time = System.currentTimeMillis();
        info.getEvent().replyEmbeds(MessageUtils.createInfoEmbed("Current Gateaway latency: " + BotConnection.getJda().getGatewayPing() + "ms\nResponse time: measuring...").build())
                .addActionRow(Button.primary("ping", "Refresh"))
                .queue(message -> message.editOriginalEmbeds(MessageUtils.createInfoEmbed("Current Gateaway latency: " + BotConnection.getJda().getGatewayPing() + "ms\nResponse time: " + (System.currentTimeMillis() - time) + "ms").build()).queue());
    }

    @ButtonAnnotation(id ="ping")
    public void onButtonClick(ButtonClickInfo info) {
        final long time = System.currentTimeMillis();
        Objects.requireNonNull(info.getEvent().getMessage()).editMessage(MessageUtils.createInfoEmbed("Current Gateaway latency: " + BotConnection.getJda().getGatewayPing() + "ms\nResponse time: measuring...").build())
                .queue(message -> message.editMessage(MessageUtils.createInfoEmbed("Current Gateaway latency: " + BotConnection.getJda().getGatewayPing() + "ms\nResponse time: " + (System.currentTimeMillis() - time) + "ms").build()).queue());
        info.getEvent().reply("Updated the ping!").setEphemeral(true).queue();
    }
}
