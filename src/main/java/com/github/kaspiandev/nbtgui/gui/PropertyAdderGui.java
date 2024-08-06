package com.github.kaspiandev.nbtgui.gui;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PropertyAdderGui {

    private static final Map<UUID, PropertyAdderGui> GUI_CACHE = new HashMap<>();
    private static final ItemStack FILLER = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    private static final String[] MASK = new String[]{
            "xxxxxxxxx",
            "x ntv a x",
            "xxxxxxxxx"
    };
    private final NBTGui plugin;
    private final Player player;
    private final InventoryGui gui;
    private String name;
    private Class<?> type;
    private Object value;

    public PropertyAdderGui(NBTGui plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.gui = buildGui();
    }

    private static void cacheGui(Player player, PropertyAdderGui gui) {
        GUI_CACHE.put(player.getUniqueId(), gui);
    }

    public static PropertyAdderGui getCachedOrBuild(NBTGui plugin, Player player) {
        return GUI_CACHE.computeIfAbsent(player.getUniqueId(), (uuid) -> new PropertyAdderGui(plugin, player));
    }

    public static void destroy(Player player) {
        GUI_CACHE.remove(player.getUniqueId());
    }

    private InventoryGui buildGui() {
        InventoryGui gui = new InventoryGui(plugin, ColorUtil.string("&8&lItem Properties"), MASK);

        DynamicGuiElement nameElement = new DynamicGuiElement('n', () -> {
            ItemStack nameItem = new ItemStack(Material.NAME_TAG);
            ItemMeta meta = nameItem.getItemMeta();
            assert meta != null;

            meta.setDisplayName(ColorUtil.string("&6&lName"));
            List<String> lore = new ArrayList<>();
            if (name == null) {
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                lore.add("&7Current name: " + name);
                lore.add("");
            }

            lore.add("&7Click to change");
            meta.setLore(lore.stream()
                             .map(ColorUtil::string)
                             .toList());

            nameItem.setItemMeta(meta);

            return new StaticGuiElement('n', nameItem); // TODO: Open name input anvil gui
        });
        gui.addElement(nameElement);

        DynamicGuiElement typeElement = new DynamicGuiElement('t', () -> {
            ItemStack typeItem = new ItemStack(Material.BOOK);
            ItemMeta meta = typeItem.getItemMeta();
            assert meta != null;

            meta.setDisplayName(ColorUtil.string("&6&lType"));
            List<String> lore = new ArrayList<>();
            if (type == null) {
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                lore.add("&7Current Type: " + type.getSimpleName());
                lore.add("");
            }

            lore.add("&7Click to change");
            meta.setLore(lore.stream()
                             .map(ColorUtil::string)
                             .toList());

            typeItem.setItemMeta(meta);

            return new StaticGuiElement('t', typeItem); // TODO: Open type input list gui
        });
        gui.addElement(typeElement);

        DynamicGuiElement valueElement = new DynamicGuiElement('v', () -> {
            ItemStack valueItem = new ItemStack(Material.OAK_SIGN);
            ItemMeta meta = valueItem.getItemMeta();
            assert meta != null;

            meta.setDisplayName(ColorUtil.string("&6&lType"));
            List<String> lore = new ArrayList<>();
            if (type == null) {
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                lore.add("&7Current Value: " + value);
                lore.add("");
            }

            lore.add("&7Click to change");
            meta.setLore(lore.stream()
                             .map(ColorUtil::string)
                             .toList());

            valueItem.setItemMeta(meta);

            return new StaticGuiElement('v', valueItem); // TODO: Open value input gui
        });
        gui.addElement(valueElement);

        gui.addElement(new StaticGuiElement('x', FILLER));

        return gui;
    }

    private void redraw() {
        gui.draw(player, true, false);
    }

    public void open() {
        gui.show(player);
    }

}
