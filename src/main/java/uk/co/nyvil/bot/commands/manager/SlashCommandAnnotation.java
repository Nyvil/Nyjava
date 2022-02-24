package uk.co.nyvil.bot.commands.manager;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface SlashCommandAnnotation {

    String name();
    Permission neededPermission() default Permission.USE_SLASH_COMMANDS;

}