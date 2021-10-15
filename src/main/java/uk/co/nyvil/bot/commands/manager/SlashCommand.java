package uk.co.nyvil.bot.commands.manager;

import net.dv8tion.jda.api.Permission;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;

public interface SlashCommand {

    void execute(SlashCommandExecutionInfo info);
    Permission neededPermission(); // default is "USE_SLASH_COMMANDS"
}
