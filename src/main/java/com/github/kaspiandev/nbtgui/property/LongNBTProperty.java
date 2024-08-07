package com.github.kaspiandev.nbtgui.property;

import com.github.kaspiandev.nbtgui.property.value.PrettyLongValue;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LongNBTProperty extends NBTProperty<Long> {

    public LongNBTProperty(String name, long value) {
        super(name, value);
    }

    @Override
    public PrettyLongValue getPrettyValue() {
        return new PrettyLongValue(value);
    }

    @Override
    public NBTType getNBTType() {
        return NBTType.NBTTagLong;
    }

    @Override
    protected ItemStack getDisplayItem() {
        ItemStack item = new ItemStack(Material.LIGHTNING_ROD);

        ItemMeta meta = item.getItemMeta();
        assert meta != null; // Meta cannot be null for LIGHTNING_ROD

        item.setAmount((int) Math.max(1, Math.min(Math.abs(value), 64)));

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
        nbtEntity.setLong(name, value);
    }

}
