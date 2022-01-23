package uk.co.nyvil.bot.buttons.manager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface ButtonAnnotation {

    String id();
}
