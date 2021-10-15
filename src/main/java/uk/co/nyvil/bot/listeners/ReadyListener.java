package uk.co.nyvil.bot.listeners;

/**
 * Created by Nyvil on 11/06/2021 at 21:26
 * in project Discord Base
 */

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class ReadyListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if(genericEvent instanceof ReadyEvent) {
            System.out.println("Bot ready! uwu (>w<)");
        }
    }
}
