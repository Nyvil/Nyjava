package uk.co.nyvil.bot.commands.manager;

/**
 * Created by Nyvil on 11/06/2021 at 21:27
 * in project Discord Base
 */

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import uk.co.nyvil.bot.BotConnection;
import uk.co.nyvil.bot.commands.commands.*;
import uk.co.nyvil.bot.commands.status.SlashCommandExecutionInfo;

import java.util.*;

@Getter
public class CommandHandler {


    CommandListUpdateAction commands = BotConnection.getJda().updateCommands();

    @Getter
    public final Map<SlashCommandAnnotation, SlashCommand> slashCommandMap = new HashMap<>();

    public CommandHandler() {

        System.out.println("[Debug] Current registered Commands:");
        BotConnection.getJda().retrieveCommands().complete().forEach(command -> {
            System.out.println(command.getName() + " | " + command.getDescription() + " | " + command.getApplicationId());
        });

        //Guild guild = BotConnection.getJda().getGuildById("guildid"); --> If you want to test a command, it's better uploading it to a test guild cuz it's instant, unlike registering slash commands globally
        //BotConnection.getJda().upsertCommand(addCommandData("ping", "Shows the bot's latency")).queue();
        //BotConnection.getJda().upsertCommand(addCommandData("shutdown", "closes the bot connection")).queue(); // --> Have this uncommented the first time you run the bot. Don't do that more than once, otherwise it'll upload that command every time!
        //BotConnection.getJda().upsertCommand(addCommandData("fox", "Show's a fox picture")).queue();

        /*if(guild != null) {
            guild.upsertCommand(addCommandData("shutdown", "closes the bot connection")).queue();

            guild.upsertCommand(addCommandData("ping", "debug")).queue();

        }*/

        try {

            addSlashCommand(PingCommand.class.getDeclaredMethod("execute", SlashCommandExecutionInfo.class).getAnnotation(SlashCommandAnnotation.class), new PingCommand());
            addSlashCommand(ShutdownCommand.class.getDeclaredMethod("execute", SlashCommandExecutionInfo.class).getAnnotation(SlashCommandAnnotation.class), new ShutdownCommand());
            addSlashCommand(FoxCommand.class.getDeclaredMethod("execute", SlashCommandExecutionInfo.class).getAnnotation(SlashCommandAnnotation.class), new FoxCommand());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void addSlashCommand(SlashCommandAnnotation slashCommandAnnotation, SlashCommand command) {
        slashCommandMap.put(slashCommandAnnotation, command);
    }

    private CommandData addCommandData(String name, String description, OptionData... options) {
        return new CommandData(name, description).addOptions(options);
    }

}
