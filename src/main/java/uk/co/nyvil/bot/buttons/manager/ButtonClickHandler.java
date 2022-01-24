package uk.co.nyvil.bot.buttons.manager;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import uk.co.nyvil.Bot;

import java.util.Objects;

public class ButtonClickHandler extends ListenerAdapter {

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if(event.getGuild() == null) return;
        if(event.getUser().isBot()) return;
        if(event.getMember() == null) return;


        if (Bot.getInstance().getButtonhandler().getButtonMap().containsKey(Objects.requireNonNull(event.getButton()).getId())) {
//TODO: Finish this, computer crashed so I lost a good bit of code
        }

    }
}
