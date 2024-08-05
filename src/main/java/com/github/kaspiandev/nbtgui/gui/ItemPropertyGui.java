package com.github.kaspiandev.nbtgui.gui;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.parser.NBTParser;
import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemPropertyGui {

    private static final String[] MASK = new String[]{
            "xxxxxxxxx",
            "xeeeeeeex",
            "xeeeeeeex",
            "xeeeeeeex",
            "xxxxxxxxx"
    };
    // TODO: Cache
    private final NBTGui plugin;
    private final InventoryGui gui;

    public ItemPropertyGui(NBTGui plugin, ItemStack item) {
        this.plugin = plugin;
        this.gui = buildGui(item);
    }

    private InventoryGui buildGui(ItemStack item) {
        InventoryGui gui = new InventoryGui(plugin, ColorUtil.string("&8&lItem Properties"), MASK);

        // TODO: Add properties as gui items, sort by type
        List<NBTProperty<?>> properties = NBTParser.INSTANCE.parse(NBT.readNbt(item));
        Collections.sort(properties);

        gui.addElement(new StaticGuiElement('x', new ItemStack(Material.GRAY_STAINED_GLASS_PANE)));

        GuiElementGroup group = new GuiElementGroup('e');
        properties.forEach((property) -> {
            group.addElement(property.bakeElement('i'));
        });

        gui.addElement(group);

        return gui;
    }

    public void open(Player player) {
        // TODO: Send message if no props
        gui.show(player);
    }

}
