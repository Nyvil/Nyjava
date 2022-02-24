package uk.co.nyvil.bot.commands.manager;

import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;

public interface SlashCommand {

    void execute(SlashCommandExecutionInfo info);
}
