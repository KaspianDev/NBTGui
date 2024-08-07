package com.github.kaspiandev.nbtgui.gui;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.property.registry.PropertyFactory;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.GuiElementGroup;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TypeInputGui {

    private static final String[] MASK = new String[]{
            "xxxxxxxxx",
            "xtttttttx",
            "cxxxxxxxx"
    };
    private static final ItemStack FILLER = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    private final NBTGui plugin;
    private final PropertyAdderGui adderGui;
    private final InventoryGui gui;

    public TypeInputGui(NBTGui plugin, PropertyAdderGui adderGui) {
        this.plugin = plugin;
        this.adderGui = adderGui;
        this.gui = buildGui();
    }

    private InventoryGui buildGui() {
        InventoryGui gui = new InventoryGui(
                plugin,
                ColorUtil.string("&8&lItem Properties"),
                MASK);

        gui.setCloseAction((action) -> {
            adderGui.getGui().show(adderGui.getPlayer());
            return false;
        });

        ItemStack cancelItem = new ItemStack(Material.RED_DYE);
        ItemMeta cancelMeta = cancelItem.getItemMeta();
        assert cancelMeta != null;

        cancelMeta.setDisplayName(ColorUtil.string("&c&lCancel"));

        List<String> cancelLore = List.of(
                ColorUtil.string("&7Click to exit this menu!")
        );
        cancelMeta.setLore(cancelLore);

        cancelItem.setItemMeta(cancelMeta);

        gui.addElement(new StaticGuiElement('c', cancelItem, (action) -> {
            adderGui.getGui().show(adderGui.getPlayer());
            Bukkit.getScheduler().runTaskLater(plugin, gui::destroy, 2);
            return true;
        }));

        gui.addElement(new StaticGuiElement('x', FILLER));

        GuiElementGroup group = new GuiElementGroup('t');
        PropertyFactory.getIndexedClasses().forEach((clazz) -> {
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;

            meta.setDisplayName(ColorUtil.string("&e&l" + clazz.getSimpleName()));

            List<String> lore = List.of(
                    ColorUtil.string("&7Click to select")
            );
            meta.setLore(lore);

            item.setItemMeta(meta);

            group.addElement(new StaticGuiElement('i', item, (action) -> {
                adderGui.setType(clazz);
                adderGui.setProperty(null);
                adderGui.getGui().show(adderGui.getPlayer());
                Bukkit.getScheduler().runTaskLater(plugin, gui::destroy, 2);
                return true;
            }));
        });

        gui.addElement(group);

        return gui;
    }

    public InventoryGui getGui() {
        return gui;
    }

}
