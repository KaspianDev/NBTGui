package com.github.kaspiandev.nbtgui.property.reader;

import com.github.kaspiandev.nbtgui.property.IntegerNBTProperty;
import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.property.StringNBTProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PropertyFactory {

    private static final Map<String, Class<?>> CLASS_INDEX = new HashMap<>();
    private static final Map<Class<?>, Build<?>> TYPE_TO_PROPERTY = new HashMap<>();

    static {
        register(String.class, StringNBTProperty::new);
        register(Integer.class, IntegerNBTProperty::new);
    }

    private PropertyFactory() {}

    public static <T> void register(Class<T> clazz, Build<T> function) {
        TYPE_TO_PROPERTY.put(clazz, function);
        CLASS_INDEX.put(clazz.getSimpleName(), clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<NBTProperty<T>> build(String key, T value) {
        return build(key, (Class<? extends T>) value.getClass(), value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<NBTProperty<T>> build(String key, Class<? extends T> clazz, T value) {
        if (TYPE_TO_PROPERTY.containsKey(clazz)) {
            Build<T> buildFunction = (Build<T>) TYPE_TO_PROPERTY.get(clazz);
            return Optional.of(buildFunction.apply(key, value));
        } else return Optional.empty();
    }

    public static Set<String> getIndexedClassNames() {
        return CLASS_INDEX.keySet();
    }

    public static Optional<Class<?>> findIndexedClass(String name) {
        return Optional.ofNullable(CLASS_INDEX.get(name));
    }

    @FunctionalInterface
    public interface Build<T> {

        NBTProperty<T> apply(String key, T value);

    }

}
