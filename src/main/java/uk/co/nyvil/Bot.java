package uk.co.nyvil;

import uk.co.nyvil.bot.JdaService;
import uk.co.nyvil.bot.commands.manager.CommandRegistry;

/**
 * Created by Nyvil on 11/06/2021 at 21:16
 * in project Discord Base
 */

public class Bot {

    private static final Bot instance = new Bot();
    private static JdaService jdaService;
    private static CommandRegistry commandRegistry;

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Enter your bot's token!");
            System.exit(1);
        }

        jdaService = new JdaService(args[0]);
        commandRegistry = new CommandRegistry();

    }

    public static Bot getInstance() {
        return instance;
    }

    public JdaService getJdaService() {
        return jdaService;
    }

    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }
}
