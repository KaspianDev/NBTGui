package com.github.kaspiandev.nbtgui.property.registry;

import com.github.kaspiandev.nbtgui.property.*;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadableNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PropertyReader {

    private static final Map<NBTType, Reader> TYPE_TO_PROPERTY = new HashMap<>();

    static {
        register(NBTType.NBTTagString, (nbtEntity, key) -> new StringNBTProperty(key, nbtEntity.getString(key)));
        register(NBTType.NBTTagInt, (nbtEntity, key) -> new IntegerNBTProperty(key, nbtEntity.getInteger(key)));
        register(NBTType.NBTTagByte, (nbtEntity, key) -> new ByteNBTProperty(key, nbtEntity.getByte(key)));
        register(NBTType.NBTTagShort, (nbtEntity, key) -> new ShortNBTProperty(key, nbtEntity.getShort(key)));
        register(NBTType.NBTTagLong, (nbtEntity, key) -> new LongNBTProperty(key, nbtEntity.getLong(key)));
    }

    private PropertyReader() {}

    public static void register(NBTType type, Reader function) {
        TYPE_TO_PROPERTY.put(type, function);
    }

    public static Optional<NBTProperty<?>> read(ReadableNBT nbtEntity, String key) {
        NBTType type = nbtEntity.getType(key);
        if (!TYPE_TO_PROPERTY.containsKey(type)) return Optional.empty();

        return Optional.of(TYPE_TO_PROPERTY.get(type).read(nbtEntity, key));
    }

    public static Set<NBTType> getRegisteredTypes() {
        return TYPE_TO_PROPERTY.keySet();
    }

    @FunctionalInterface
    public interface Reader {

        NBTProperty<?> read(ReadableNBT nbtEntity, String key);

    }

}
