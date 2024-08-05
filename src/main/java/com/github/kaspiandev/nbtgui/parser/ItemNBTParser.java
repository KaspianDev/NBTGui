package com.github.kaspiandev.nbtgui.parser;

import com.github.kaspiandev.nbtgui.property.NBTProperty;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ItemNBTParser implements NBTParser<ItemStack> {

    @Override
    public List<NBTProperty<?>> parse(ItemStack item) {
        return new ArrayList<>(
                Stream.concat(
                        readAll(NBT.itemStackToNBT(item)).stream(),
                        readAll(NBT.readNbt(item)).stream()).toList()
        );
    }

}
