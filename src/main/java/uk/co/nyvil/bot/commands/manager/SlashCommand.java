package uk.co.nyvil.bot.commands.manager;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import uk.co.nyvil.bot.commands.status.SlashCommandRecord;

public abstract class SlashCommand {

    public abstract String name();
    public abstract String description();
    public abstract Permission neededPermission();
    public abstract boolean guildOnly();
    public abstract OptionData[] options();
    public abstract void execute(SlashCommandRecord info);
}
