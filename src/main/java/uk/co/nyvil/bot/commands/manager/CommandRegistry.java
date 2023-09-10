package uk.co.nyvil.bot.commands.manager;

/**
 * Created by Nyvil on 11/06/2021 at 21:27
 * in project Discord Base
 */

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import uk.co.nyvil.Bot;
import uk.co.nyvil.bot.commands.commands.FoxCommand;
import uk.co.nyvil.bot.commands.commands.PingCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandRegistry {

    private final Optional<JDA> jda = Bot.getInstance().getJdaService().getJda();
    private List<SlashCommand> activeCommands = new ArrayList<>();

    public CommandRegistry() {
        registerCommands();
    }

    private void registerCommands() {
        jda.ifPresentOrElse(jda -> {
            jda.updateCommands().addCommands(
                    create(new PingCommand()),
                    create(new FoxCommand())
            ).queue();
        }, () -> System.out.println("JDA is not present!"));
    }

    private CommandData create(SlashCommand command) {
        this.activeCommands.add(command);
        if(command.options().length > 0) {
            System.out.println("Registering command " + command.name() + " with " + command.options().length + " options!");
            return Commands.slash(command.name(), command.description()).addOptions(command.options()).setGuildOnly(command.guildOnly());
        } else {
            System.out.println("Registering command " + command.name() + " with no options!");
            return Commands.slash(command.name(), command.description()).setGuildOnly(command.guildOnly());
        }
    }

    public List<SlashCommand> getActiveCommands() {
        return activeCommands;
    }
}
