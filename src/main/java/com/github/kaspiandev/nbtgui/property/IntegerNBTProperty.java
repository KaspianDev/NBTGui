package com.github.kaspiandev.nbtgui.property;

import com.github.kaspiandev.nbtgui.property.value.PrettyIntegerValue;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IntegerNBTProperty extends NBTProperty<Integer> {

    public IntegerNBTProperty(String name, Integer value) {
        super(name, value);
    }

    @Override
    public PrettyIntegerValue getPrettyValue() {
        return new PrettyIntegerValue(value);
    }

    @Override
    public NBTType getNBTType() {
        return NBTType.NBTTagInt;
    }

    @Override
    protected ItemStack getDisplayItem() {
        ItemStack item = new ItemStack(Material.STICK);

        ItemMeta meta = item.getItemMeta();
        assert meta != null; // Meta cannot be null for STICK

        item.setAmount(Math.max(1, Math.min(value, 64)));

        meta.setDisplayName(ColorUtil.string("&d&l" + value.getClass().getSimpleName()));
        meta.setLore(bakeLore());

        item.setItemMeta(meta);

        return item;
    }

    @Override
    public StaticGuiElement bakeElement(char slotChar) {
        return new StaticGuiElement(slotChar, getDisplayItem());
    }

    @Override
    public void writeTo(ReadWriteNBT nbtEntity) {
        nbtEntity.setInteger(name, value);
    }

}
