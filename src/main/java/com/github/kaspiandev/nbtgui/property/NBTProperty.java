package com.github.kaspiandev.nbtgui.property;

import com.github.kaspiandev.nbtgui.property.value.PrettyValue;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class NBTProperty<T> implements Comparable<NBTProperty<?>> {

    protected final String name;
    protected final T value;

    protected NBTProperty(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends T> getClassType() {
        return (Class<? extends T>) value.getClass();
    }

    protected List<String> bakeLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Name: " + name);
        lore.add("&7Type: " + value.getClass().getSimpleName() + " (" + getNBTType().toString() + ")");
        getPrettyValue().appendTo(lore);

        return lore.stream()
                   .map(ColorUtil::string)
                   .toList();
    }

    public abstract PrettyValue<T, ?> getPrettyValue();

    public abstract NBTType getNBTType();

    protected abstract ItemStack getDisplayItem();

    public abstract StaticGuiElement bakeElement(char slotChar);

    public abstract void writeTo(ReadWriteNBT nbtEntity);

    public boolean removeFrom(ReadWriteNBT nbtEntity) {
        if (nbtEntity.hasTag(name, getNBTType())) {
            nbtEntity.removeKey(name);
            return true;
        } else return false;
    }

    @Override
    public int compareTo(NBTProperty<?> property) {
        return getClass().getName().compareTo(property.getClass().getName());
    }

}
