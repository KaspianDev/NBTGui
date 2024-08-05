package com.github.kaspiandev.nbtgui.property;

import com.github.kaspiandev.nbtgui.property.value.PrettyStringListValue;
import com.github.kaspiandev.nbtgui.property.value.PrettyValue;
import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StringNBTProperty extends NBTProperty<String> {

    public StringNBTProperty(ReadableNBT nbtEntity, String name, String value) {
        super(nbtEntity, name, value);
    }

    @Override
    public PrettyValue<String, ?> getPrettyValue() {
        return new PrettyStringListValue(value);
    }

    @Override
    public NBTType getNBTType() {
        return NBTType.NBTTagString;
    }

    @Override
    protected ItemStack getDisplayItem() {
        ItemStack item = new ItemStack(Material.PAPER);

        ItemMeta meta = item.getItemMeta();
        assert meta != null; // Meta cannot be null for PAPER
        if (value.isEmpty()) {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
        } else {
            item.setAmount(Math.min(value.length(), 64));
        }

        meta.setDisplayName("&e&l" + value.getClass().getSimpleName());
        meta.setLore(bakeLore());

        item.setItemMeta(meta);

        return item;
    }

    @Override
    public StaticGuiElement bakeElement(char slotChar) {
        return new StaticGuiElement(slotChar, getDisplayItem());
    }

}
