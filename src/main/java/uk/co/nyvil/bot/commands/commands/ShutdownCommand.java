package uk.co.nyvil.bot.commands.commands;

import uk.co.nyvil.bot.commands.manager.SlashCommand;
import uk.co.nyvil.bot.commands.manager.SlashCommandAnnotation;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;
import uk.co.nyvil.utils.MessageUtils;
import uk.co.nyvil.utils.PermissionUtils;

public class ShutdownCommand implements SlashCommand {


    @Override
    @SlashCommandAnnotation(name = "shutdown")
    public void execute(SlashCommandExecutionInfo info) {
        if(PermissionUtils.isHighStaff(info.getEvent().getUser())) {
            info.getEvent().replyEmbeds(MessageUtils.createSuccessEmbed("Received shutdown command!").build()).queue(interactionHook -> {
                interactionHook.getJDA().shutdown();
                interactionHook.getJDA().shutdownNow();
                interactionHook.getJDA().getHttpClient().dispatcher().executorService().shutdownNow();
                interactionHook.getJDA().getHttpClient().connectionPool().evictAll();
            });
        } else {
            info.getEvent().replyEmbeds(MessageUtils.createErrorEmbed("You don't have permission to execute this command!").build()).queue();
        }

    }
}
