package uk.co.nyvil.bot;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import uk.co.nyvil.Bot;
import uk.co.nyvil.bot.commands.manager.SlashCommandHandler;
import uk.co.nyvil.bot.listeners.ReadyListener;

import javax.security.auth.login.LoginException;

/**
 * Created by Nyvil on 11/06/2021 at 21:25
 * in project Discord Base
 */

public class BotConnection {

    @Getter
    private static JDA jda;

    public BotConnection() {
        start();
    }

    private void start() {
        try {
            jda = JDABuilder.createDefault(Bot.getInstance().getToken())
                    .addEventListeners(new ReadyListener())
                    .addEventListeners(new SlashCommandHandler())
                    .setActivity(Activity.watching("Anime ^-^"))
                    .build();

            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void stop() {
        jda.shutdown();
    }

}
