package uk.co.nyvil.bot.buttons.manager;

import lombok.Getter;
import uk.co.nyvil.bot.buttons.status.ButtonClickInfo;
import uk.co.nyvil.bot.commands.commands.PingCommand;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ButtonHandler {
    @Getter
    public final Map<String, ButtonMethods> buttonMap = new HashMap<>();

    /**
     * Declare all classes that contain buttons in this class
     */
    public ButtonHandler(){
        registerButtons(PingCommand.class);
    }
    private void registerButtons(Object... objs) {
        for (Object obj : objs) {
            final Class<?> cls = obj.getClass();

            Arrays.stream(cls.getDeclaredMethods())
                .filter(method ->
                        (method.getModifiers() & (Modifier.PROTECTED | Modifier.PRIVATE)) == 0
                                && method.isAnnotationPresent(ButtonAnnotation.class)
                                && method.getParameterCount() == 1
                                && method.getParameterTypes()[0] == ButtonClickInfo.class
                )
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> {
                    final String id = method.getAnnotation(ButtonAnnotation.class).id();

                    if (!id.isEmpty()) {
                        buttonMap.put(id, new ButtonMethods(cls, method));
                    } else {
                        System.err.println("No declared buttons found in " + cls.getName() + ".");
                    }
                });
        }
    }
}
