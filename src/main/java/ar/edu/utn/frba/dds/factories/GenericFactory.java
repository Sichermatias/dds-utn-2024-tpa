// GenericFactory.java
package ar.edu.utn.frba.dds.factories;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GenericFactory {
    private static final Map<Class<?>, Object> defaultValues = new HashMap<>();

    static {
        defaultValues.put(String.class, "");
        defaultValues.put(Integer.class, 0);
        defaultValues.put(int.class, 0);
        defaultValues.put(Boolean.class, false);
        defaultValues.put(boolean.class, false);
        defaultValues.put(LocalDate.class, LocalDate.now());
        // Add other default values as needed
    }

    public static <T> T createInstance(Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (defaultValues.containsKey(field.getType())) {
                    field.set(instance, defaultValues.get(field.getType()));
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }
}