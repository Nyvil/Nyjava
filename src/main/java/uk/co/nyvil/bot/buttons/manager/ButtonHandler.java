package uk.co.nyvil.bot.buttons.manager;

import uk.co.nyvil.bot.buttons.status.ButtonClickInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

public class ButtonHandler {
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
                        // we want to register it uh somewhere, maybe put the id and class/method in a
                    //TODO: Create a registry
                    }
                });
        }
    }
}
