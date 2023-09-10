package uk.co.nyvil.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import uk.co.nyvil.bot.commands.manager.SlashCommandHandler;
import uk.co.nyvil.bot.listeners.ReadyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdaService {


    private final String token;

    private final List<GatewayIntent> gatewayIntents = new ArrayList<>();
    private static JDA jda;

    public JdaService(String token) {
        this.token = token;

        start(Collections.emptyList());
    }


    /**
     * @param gatewayIntents the gatewayIntents to set - https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/requests/GatewayIntent.html. If you want none, just pass an empty List -> Collections.emptyList()
     *
     */
    private void start(List<GatewayIntent> gatewayIntents) {
        try {
            jda = JDABuilder.createDefault(token)
                    .enableIntents(gatewayIntents)
                    .addEventListeners(new SlashCommandHandler(), new ReadyListener())
                    .build();

            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Optional<JDA> getJda() {
        return Optional.ofNullable(jda);
    }



}
