package uk.co.nyvil.bot.buttons.manager;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ButtonClickHandler extends ListenerAdapter {

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if(event.getGuild() == null) return;
        if(event.getUser().isBot()) return;
        if(event.getMember() == null) return;
        //TODO: Get the method from a registry and run it

    }
}
