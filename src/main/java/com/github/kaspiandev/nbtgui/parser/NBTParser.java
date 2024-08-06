package com.github.kaspiandev.nbtgui.parser;

import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.property.registry.PropertyReader;
import de.tr7zw.nbtapi.iface.ReadableNBT;

import java.util.ArrayList;
import java.util.List;

public interface NBTParser<E> {

    List<NBTProperty<?>> parse(E entity);

    default List<NBTProperty<?>> readAll(ReadableNBT nbtEntity) {
        List<NBTProperty<?>> properties = new ArrayList<>();
        nbtEntity.getKeys().forEach((key) -> {
            PropertyReader.read(nbtEntity, key).ifPresent(properties::add);
        });
        return properties;
    }

}
