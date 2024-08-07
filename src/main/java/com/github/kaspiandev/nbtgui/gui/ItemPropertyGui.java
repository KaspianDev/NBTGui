package com.github.kaspiandev.nbtgui.gui;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.parser.ItemNBTParser;
import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.DynamicGuiElement;
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

    private final NBTGui plugin;
    private final Player player;
    private final ItemStack item;
    private InventoryGui gui;

    public ItemPropertyGui(NBTGui plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.item = player.getInventory().getItemInMainHand();
        this.gui = buildGui();
    }

    private InventoryGui buildGui() {
        InventoryGui gui = new InventoryGui(plugin, ColorUtil.string("&8&lItem Properties"), MASK);

        List<NBTProperty<?>> properties = new ItemNBTParser().parse(item);
        Collections.sort(properties);

        gui.addElement(new DynamicGuiElement('p', () -> {
            if (gui.getPageNumber(player) == 0) {
                return new StaticGuiElement('p', FILLER);
            } else {
                ItemStack previousPageItem = new ItemStack(Material.ARROW);
                ItemMeta previousPageItemMeta = previousPageItem.getItemMeta();
                assert previousPageItemMeta != null;

                previousPageItemMeta.setDisplayName(ColorUtil.string("&6&lPrevious Page"));
                previousPageItemMeta.setLore(List.of(
                        "",
                        ColorUtil.string("&7Click to go to the previous page!")
                ));

                previousPageItem.setItemMeta(previousPageItemMeta);

                return new StaticGuiElement('p', previousPageItem, (action) -> {
                    gui.setPageNumber(gui.getPageNumber(player) - 1);
                    gui.draw(player, true, false);
                    return true;
                });
            }
        }));

        gui.addElement(new DynamicGuiElement('n', () -> {
            if (gui.getPageNumber(player) == gui.getPageAmount(player) - 1) {
                return new StaticGuiElement('n', FILLER);
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

                return new StaticGuiElement('n', nextPageItem, (action) -> {
                    gui.setPageNumber(gui.getPageNumber(player) + 1);
                    gui.draw(player, true, false);
                    return true;
                });
            }
        }));

        ItemStack addItem = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = addItem.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ColorUtil.string("&a&lAdd"));

        List<String> lore = List.of(
                ColorUtil.string("&7Click to add a new property to this item!")
        );
        meta.setLore(lore);

        addItem.setItemMeta(meta);

        gui.addElement(new StaticGuiElement('a', addItem, (action) -> {
            new PropertyAdderGui(this).getGui().show(player);
            return true;
        }));

        gui.addElement(new StaticGuiElement('x', FILLER));

        GuiElementGroup group = new GuiElementGroup('e');
        properties.forEach((property) -> {
            group.addElement(property.bakeElement('i'));
        });

        gui.addElement(group);

        return gui;
    }

    public InventoryGui getGui() {
        return gui;
    }

    public ItemStack getItem() {
        return item;
    }

    public void rebuild() {
        gui = buildGui();
    }

    public Player getPlayer() {
        return player;
    }

    public NBTGui getPlugin() {
        return plugin;
    }

}
