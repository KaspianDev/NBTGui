package com.github.kaspiandev.nbtgui.property;

import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class StringNBTProperty extends NBTProperty<String> {

    public StringNBTProperty(ReadableNBT nbtEntity, String name, String value) {
        super(nbtEntity, name, value);
    }

    @Override
    public NBTType getNBTType() {
        return NBTType.NBTTagString;
    }

    @Override
    protected ItemStack getDisplayItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        item.setAmount(Math.min(value.length(), 64));

        ItemMeta meta = item.getItemMeta();
        assert meta != null; // Meta cannot be null for PAPER

        List<String> additionalLines = List.of(); // Todo

        return null;
    }

    @Override
    public StaticGuiElement bakeElement(char slotChar) {
        StaticGuiElement element = new StaticGuiElement(slotChar, getDisplayItem());

        return null;
    }

}
