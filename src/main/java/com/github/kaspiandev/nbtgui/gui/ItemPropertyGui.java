package com.github.kaspiandev.nbtgui.gui;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.parser.ItemNBTParser;
import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class ItemPropertyGui {

    private static final ItemStack FILLER = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    private static final String[] MASK = new String[]{
            "xxxxxxxxx",
            "xeeeeeeex",
            "xeeeeeeex",
            "xeeeeeeex",
            "xxxpxnxxa"
    };
    // TODO: Cache
    // TODO: Add buttons to create NBT properties
    private final NBTGui plugin;

    public ItemPropertyGui(NBTGui plugin) {
        this.plugin = plugin;
    }

    private InventoryGui buildGui(ItemStack item, Player player) {
        InventoryGui gui = new InventoryGui(plugin, ColorUtil.string("&8&lItem Properties"), MASK);

        // TODO: Add properties as gui items, sort by type
        List<NBTProperty<?>> properties = new ItemNBTParser().parse(item);
        Collections.sort(properties);

        int page = gui.getPageNumber(player);
        if (page == 0) {
            gui.addElement(new StaticGuiElement('p', FILLER));
        } else {
            // TODO: Configurable pagination items
            ItemStack previousPageItem = new ItemStack(Material.ARROW);
            ItemMeta previousPageItemMeta = previousPageItem.getItemMeta();
            assert previousPageItemMeta != null;

            previousPageItemMeta.setDisplayName(ColorUtil.string("&6&lPrevious Page"));
            previousPageItemMeta.setLore(List.of(
                    "",
                    ColorUtil.string("&7Click to go to the previous page!")
            ));

            previousPageItem.setItemMeta(previousPageItemMeta);

            gui.addElement(new StaticGuiElement('p', previousPageItem, (action) -> {
                gui.setPageNumber(page - 1);
                gui.draw(player);
                return true;
            }));
        }

        if (page >= gui.getPageAmount(player)) {
            gui.addElement(new StaticGuiElement('p', FILLER));
        } else {
            ItemStack nextPageItem = new ItemStack(Material.ARROW);
            ItemMeta nextPageItemMeta = nextPageItem.getItemMeta();
            assert nextPageItemMeta != null;

            nextPageItemMeta.setDisplayName(ColorUtil.string("&6&lNext Page"));
            nextPageItemMeta.setLore(List.of(
                    "",
                    ColorUtil.string("&7Click to go to the next page!")
            ));

            nextPageItem.setItemMeta(nextPageItemMeta);

            gui.addElement(new StaticGuiElement('n', nextPageItem, (action) -> {
                gui.setPageNumber(page + 1);
                gui.draw(player);
                return true;
            }));
        }

        gui.addElement(new StaticGuiElement('x', FILLER));

        GuiElementGroup group = new GuiElementGroup('e');
        properties.forEach((property) -> {
            group.addElement(property.bakeElement('i'));
        });

        gui.addElement(group);

        return gui;
    }

    public void open(Player player, ItemStack item) {
        // TODO: Send message if no props
        buildGui(item, player).show(player);
    }

}
