package com.github.kaspiandev.nbtgui.property;

import com.github.kaspiandev.nbtgui.property.value.PrettyByteValue;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBTType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ByteNBTProperty extends NBTProperty<Byte> {

    public ByteNBTProperty(String name, byte value) {
        super(name, value);
    }

    @Override
    public PrettyByteValue getPrettyValue() {
        return new PrettyByteValue(value);
    }

    @Override
    public NBTType getNBTType() {
        return NBTType.NBTTagByte;
    }

    @Override
    protected ItemStack getDisplayItem() {
        ItemStack item = new ItemStack(Material.STONE_BUTTON);

        ItemMeta meta = item.getItemMeta();
        assert meta != null; // Meta cannot be null for STONE_BUTTON
        item.setAmount(Math.abs(value));

        meta.setDisplayName(ColorUtil.string("&d&l" + value.getClass().getSimpleName()));
        meta.setLore(bakeLore());

        item.setItemMeta(meta);

        return item;
    }

    @Override
    public StaticGuiElement bakeElement(char slotChar) {
        return new StaticGuiElement(slotChar, getDisplayItem());
    }

}
