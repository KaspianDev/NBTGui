package com.github.kaspiandev.nbtgui.property;

import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class NBTProperty<T> {

    protected final ReadableNBT nbtEntity;
    protected final String name;
    protected final T value;

    protected NBTProperty(ReadableNBT nbtEntity, String name, T value) {
        this.nbtEntity = nbtEntity;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    protected List<String> getLore(List<String> additionalLines) {
        List<String> lore = new ArrayList<>();
        lore.add("&7Type: " + getNBTType().toString() + " (" + value.getClass().getSimpleName() + ")");
        lore.add("");
        lore.addAll(additionalLines);

        return lore;
    }

    protected abstract NBTType getNBTType();

    protected abstract ItemStack getDisplayItem();

    protected abstract StaticGuiElement bakeElement(char slotChar);

}
