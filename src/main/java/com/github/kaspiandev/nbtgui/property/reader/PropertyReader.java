package com.github.kaspiandev.nbtgui.property.reader;

import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.property.StringNBTProperty;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadableNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PropertyReader {

    private static final Map<NBTType, PropertyRead> TYPE_TO_PROPERTY = new HashMap<>();

    static {
        register(NBTType.NBTTagString, (nbtEntity, key) -> new StringNBTProperty(nbtEntity, key, nbtEntity.getString(key)));
    }

    private PropertyReader() {}

    public static void register(NBTType type, PropertyRead function) {
        TYPE_TO_PROPERTY.put(type, function);
    }

    public static Optional<NBTProperty<?>> read(ReadableNBT nbtEntity, String key) {
        NBTType type = nbtEntity.getType(key);
        if (!TYPE_TO_PROPERTY.containsKey(type)) return Optional.empty();

        return Optional.of(TYPE_TO_PROPERTY.get(type).apply(nbtEntity, key));
    }

    @FunctionalInterface
    public interface PropertyRead {

        NBTProperty<?> apply(ReadableNBT nbtEntity, String key);

    }

}
