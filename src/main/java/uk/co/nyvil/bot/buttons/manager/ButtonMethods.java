package uk.co.nyvil.bot.buttons.manager;

import lombok.Getter;

import java.lang.reflect.Method;

@Getter
public class ButtonMethods {
    private final Object obj;
    private final Method method;

    ButtonMethods(Object obj, Method method) {
        this.obj = obj;
        this.method = method;
    }
}
