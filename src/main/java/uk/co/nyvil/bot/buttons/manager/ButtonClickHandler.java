package uk.co.nyvil.bot.buttons.manager;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import uk.co.nyvil.Bot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class ButtonClickHandler extends ListenerAdapter {

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if(event.getGuild() == null) return;
        if(event.getUser().isBot()) return;
        if(event.getMember() == null) return;

        String componentID = event.getComponentId();
        Map<String, ButtonMethods> buttonMap = Bot.getInstance().getButtonhandler().getButtonMap();
        if (Bot.getInstance().getButtonhandler().getButtonMap().containsKey(componentID)) {
            ButtonMethods buttonMethod = Bot.getInstance().getButtonhandler().getButtonMap().get(componentID);
            final Method method = buttonMethod.getMethod();

            try {
                method.invoke(buttonMethod.getObj(), event);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }
}
