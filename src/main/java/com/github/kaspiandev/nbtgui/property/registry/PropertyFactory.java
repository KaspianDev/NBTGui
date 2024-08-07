package com.github.kaspiandev.nbtgui.property.registry;

import com.github.kaspiandev.nbtgui.property.ByteNBTProperty;
import com.github.kaspiandev.nbtgui.property.IntegerNBTProperty;
import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.property.StringNBTProperty;

import java.util.*;

public class PropertyFactory {

    private static final Map<String, Class<?>> CLASS_INDEX = new HashMap<>();
    private static final Map<Class<?>, Factory<?>> TYPE_TO_PROPERTY = new HashMap<>();
    private static List<String> CLASS_NAME_CACHE;

    static {
        rebuildClassNameCache();

        register(String.class, new Factory<>(
                StringNBTProperty::new,
                (value) -> Optional.of(value.toString())));
        register(Integer.class, new Factory<>(
                IntegerNBTProperty::new,
                (value) -> {
                    if (value instanceof String string) {
                        try {
                            return Optional.of(Integer.parseInt(string));
                        } catch (NumberFormatException ex) {
                            return Optional.empty();
                        }
                    } else return Optional.empty();
                }
        ));
        register(Byte.class, new Factory<>(
                ByteNBTProperty::new,
                (value) -> {
                    if (value instanceof String string) {
                        try {
                            return Optional.of(Byte.parseByte(string));
                        } catch (NumberFormatException ex) {
                            return Optional.empty();
                        }
                    } else return Optional.empty();
                }
        ));
    }

    private PropertyFactory() {}

    private static void rebuildClassNameCache() {
        CLASS_NAME_CACHE = List.copyOf(CLASS_INDEX.keySet());
    }

    public static <T> void register(Class<T> clazz, Factory<T> function) {
        TYPE_TO_PROPERTY.put(clazz, function);
        CLASS_INDEX.put(clazz.getSimpleName(), clazz);

        rebuildClassNameCache();
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<NBTProperty<T>> build(String key, Class<? extends T> clazz, T value) {
        if (TYPE_TO_PROPERTY.containsKey(clazz)) {
            Factory<T> factory = (Factory<T>) TYPE_TO_PROPERTY.get(clazz);
            if (clazz.isInstance(value)) {
                Builder<T> buildFunction = factory.builder();
                return Optional.of(buildFunction.build(key, value));
            } else {
                return TYPE_TO_PROPERTY.get(clazz).adapter.tryAdapt(value)
                                                          .map((parsed) -> factory.builder.build(key, (T) parsed));
            }
        } else return Optional.empty();
    }

    public static List<String> getIndexedClassNames() {
        return CLASS_NAME_CACHE;
    }

    public static Collection<Class<?>> getIndexedClasses() {
        return CLASS_INDEX.values();
    }

    public static Optional<Class<?>> findIndexedClass(String name) {
        return Optional.ofNullable(CLASS_INDEX.get(name));
    }

    @FunctionalInterface
    public interface Builder<T> {

        NBTProperty<T> build(String key, T value);

    }

    @FunctionalInterface
    public interface Adapter<T> {

        Optional<T> tryAdapt(Object value);

    }

    public record Factory<T>(Builder<T> builder, Adapter<T> adapter) {}

}
