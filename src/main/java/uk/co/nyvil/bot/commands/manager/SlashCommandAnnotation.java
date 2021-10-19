package uk.co.nyvil.bot.commands.manager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface SlashCommandAnnotation {

    String name();

}
