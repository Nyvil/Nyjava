package uk.co.nyvil.bot.buttons.status;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import uk.co.nyvil.bot.buttons.manager.ButtonAnnotation;

@Getter
@Setter
public class ButtonClickInfo {
    private final ButtonClickEvent event;
    private final Member clicker;
    private final TextChannel textChannel;
    private final ButtonAnnotation annotation;
    private final String id;

    public ButtonClickInfo(ButtonClickEvent event, Member clicker, TextChannel textChannel, ButtonAnnotation annotation, String id) {
        this.event = event;
        this.clicker = clicker;
        this.textChannel = textChannel;
        this.annotation = annotation;
        this.id = id;
    }
}
