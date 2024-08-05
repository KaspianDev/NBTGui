package com.github.kaspiandev.nbtgui.property;

import com.github.kaspiandev.nbtgui.property.value.PrettyStringListValue;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBTType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StringNBTProperty extends NBTProperty<String> {

    public StringNBTProperty(String name, String value) {
        super(name, value);
    }

    @Override
    public PrettyStringListValue getPrettyValue() {
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

        meta.setDisplayName(ColorUtil.string("&e&l" + value.getClass().getSimpleName()));
        meta.setLore(bakeLore());

        item.setItemMeta(meta);

        return item;
    }

    @Override
    public StaticGuiElement bakeElement(char slotChar) {
        return new StaticGuiElement(slotChar, getDisplayItem());
    }

}
