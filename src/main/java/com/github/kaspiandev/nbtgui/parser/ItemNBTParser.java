package com.github.kaspiandev.nbtgui.parser;

import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.property.reader.PropertyReader;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemNBTParser implements NBTParser<ItemStack> {

    @Override
    public List<NBTProperty<?>> parse(ItemStack entity) {
        List<NBTProperty<?>> properties = new ArrayList<>();
        NBT.get(entity, (nbtEntity) -> {
            nbtEntity.getKeys().forEach((key) -> {
                PropertyReader.read(nbtEntity, key).ifPresent(properties::add);
            });
        });

        return properties;
    }

}
