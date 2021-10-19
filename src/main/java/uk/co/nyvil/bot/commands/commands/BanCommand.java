package uk.co.nyvil.bot.commands.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import uk.co.nyvil.bot.commands.manager.SlashCommand;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;
import uk.co.nyvil.utils.MessageUtils;

public class BanCommand implements SlashCommand {

    @Override
    public void execute(SlashCommandExecutionInfo info) {
        Member member = info.getEvent().getOption("member").getAsMember(); // ignore the nullable, as the member option is set as required -> can't be null
        int days = Integer.parseInt(info.getEvent().getOption("days").getAsString()); // love how JDA doesn't have the option to getAsInt()
        String reason = info.getEvent().getOption("reason").getAsString();

        if(member != null) {
            try {
                member.ban(days, reason.isEmpty() ? "No reason specified" : reason).queue();
                info.getEvent().replyEmbeds(MessageUtils.createSuccessEmbed("Successfully banned " + member.getAsMention() + " for: " + reason).build()).queue();
            }catch(Exception e) { // could theoretically throw an error if the user is trying to ban a role higher than the bot's role
                info.getEvent().replyEmbeds(MessageUtils.createErrorEmbed("Caught exception: " + e.getMessage()).build()).queue();
            }
        } else {
            info.getEvent().replyEmbeds(MessageUtils.createErrorEmbed("Couldn't find member").build()).queue();
        }
    }

    @Override
    public Permission neededPermission() {
        return Permission.BAN_MEMBERS;
    }
}
