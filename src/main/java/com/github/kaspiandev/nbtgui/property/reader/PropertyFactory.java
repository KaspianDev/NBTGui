package com.github.kaspiandev.nbtgui.property.reader;

import com.github.kaspiandev.nbtgui.property.IntegerNBTProperty;
import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.property.StringNBTProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PropertyFactory {

    private static final Map<Class<?>, Build<?>> TYPE_TO_PROPERTY = new HashMap<>();

    static {
        register(String.class, StringNBTProperty::new);
        register(Integer.class, IntegerNBTProperty::new);
    }

    private PropertyFactory() {}

    public static <T> void register(Class<T> clazz, Build<T> function) {
        TYPE_TO_PROPERTY.put(clazz, function);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<NBTProperty<T>> build(String key, T value) {
        Class<?> clazz = value.getClass();
        if (TYPE_TO_PROPERTY.containsKey(clazz)) {
            Build<T> buildFunction = (Build<T>) TYPE_TO_PROPERTY.get(clazz);
            return Optional.of(buildFunction.apply(key, value));
        } else return Optional.empty();
    }

    @FunctionalInterface
    public interface Build<T> {

        NBTProperty<T> apply(String key, T value);

    }

}
