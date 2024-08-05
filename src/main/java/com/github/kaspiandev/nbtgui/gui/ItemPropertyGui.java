package com.github.kaspiandev.nbtgui.gui;

import com.github.kaspiandev.nbtgui.NBTGui;
import de.themoep.inventorygui.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemPropertyGui {

    private static final String[] MASK = new String[]{
            "xxxxxxxxx",
            "xiiiiiiix",
            "xiiiiiiix",
            "xiiiiiiix",
            "xxxxxxxxx"
    };

    private final NBTGui plugin;

    public ItemPropertyGui(NBTGui plugin) {
        this.plugin = plugin;
    }

    private InventoryGui buildGui(ItemStack item) {
        InventoryGui gui = new InventoryGui(plugin, "&8&lItem Properties", MASK);

        // TODO: Add properties as gui items, sort by type

        return gui;
    }

    public void open(Player player) {
        // TODO: Send message if no props
        buildGui(player.getInventory().getItemInMainHand()).draw(player);
    }

}
