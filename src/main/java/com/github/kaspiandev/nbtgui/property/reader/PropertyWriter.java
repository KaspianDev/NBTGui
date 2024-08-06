package com.github.kaspiandev.nbtgui.property.reader;

import com.github.kaspiandev.nbtgui.property.NBTProperty;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PropertyWriter {

    private static final Map<Class<?>, Writer> TYPE_TO_PROPERTY = new HashMap<>();

    static {
        register(String.class, (nbtEntity, property) -> nbtEntity.setString(property.getName(), (String) property.getValue()));
        register(Integer.class, (nbtEntity, property) -> nbtEntity.setInteger(property.getName(), (int) property.getValue()));
        register(Byte.class, (nbtEntity, property) -> nbtEntity.setByte(property.getName(), (byte) property.getValue()));
    }

    private PropertyWriter() {}

    public static void register(Class<?> clazz, Writer function) {
        TYPE_TO_PROPERTY.put(clazz, function);

    }

    public static boolean write(ReadWriteNBT nbtEntity, NBTProperty<?> property) {
        Class<?> clazz = property.getValue().getClass();
        if (TYPE_TO_PROPERTY.containsKey(clazz)) {
            TYPE_TO_PROPERTY.get(clazz).write(nbtEntity, property);
            return true;
        } else return false;
    }

    public static Set<Class<?>> getRegisteredTypes() {
        return TYPE_TO_PROPERTY.keySet();
    }

    @FunctionalInterface
    public interface Writer {

        void write(ReadWriteNBT nbtEntity, NBTProperty<?> property);

    }

}
