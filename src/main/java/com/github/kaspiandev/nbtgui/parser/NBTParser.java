package com.github.kaspiandev.nbtgui.parser;

import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.property.StringNBTProperty;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadableNBT;

import java.util.ArrayList;
import java.util.List;

public abstract class NBTParser {


    public List<NBTProperty<?>> parse(ReadableNBT readableNBT) {
        List<NBTProperty<?>> properties = new ArrayList<>();
        System.out.println(readableNBT.getKeys().size());
        for (String key : readableNBT.getKeys()) {
            NBTType type = readableNBT.getType(key);
            System.out.println(key + " " + type);
            NBTProperty<?> property = switch (type) {
                case NBTTagString -> new StringNBTProperty(readableNBT, key, readableNBT.getString(key));
                default -> null; // TODO: Handle all types, extract
            };
            if (property != null) properties.add(property);
        }
        return properties;
    }

}
