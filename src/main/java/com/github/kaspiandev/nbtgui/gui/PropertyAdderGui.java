package com.github.kaspiandev.nbtgui.gui;

import com.github.kaspiandev.nbtgui.property.NBTProperty;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PropertyAdderGui {

    private static final ItemStack FILLER = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    private static final String[] MASK = new String[]{
            "xxxxxxxxx",
            "x ntv p x",
            "xxxxxxxxa"
    };
    private final ItemPropertyGui itemPropertyGui;
    private final InventoryGui gui;
    private String name;
    private Class<?> type;
    private Object value;
    private NBTProperty<?> property;

    public PropertyAdderGui(ItemPropertyGui itemPropertyGui) {
        this.itemPropertyGui = itemPropertyGui;
        this.gui = buildGui();
    }

    private InventoryGui buildGui() {
        InventoryGui gui = new InventoryGui(itemPropertyGui.getPlugin(), ColorUtil.string("&8&lItem Properties"), MASK);

        gui.setCloseAction((action) -> {
            itemPropertyGui.getGui().show(itemPropertyGui.getPlayer());
            return false;
        });

        gui.addElement(new DynamicGuiElement('n', () -> {
            ItemStack nameItem = new ItemStack(Material.NAME_TAG);
            ItemMeta meta = nameItem.getItemMeta();
            assert meta != null;

            meta.setDisplayName(ColorUtil.string("&6&lName"));
            List<String> lore = List.of(
                    ColorUtil.string("&7Click to change")
            );
            if (name == null) {
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            meta.setLore(lore);

            nameItem.setItemMeta(meta);

            return new StaticGuiElement('n', nameItem, (action) -> {
                StringPrompt namePrompt = new StringPrompt() {
                    @NotNull
                    @Override
                    public String getPromptText(@NotNull ConversationContext conversationContext) {
                        return ColorUtil.string("&6NBTGui &7» &aEnter new property name! (send 'cancel' to cancel)");
                    }

                    @Nullable
                    @Override
                    public Prompt acceptInput(@NotNull ConversationContext conversationContext, @Nullable String input) {
                        if (input != null && !input.equals("cancel")) {
                            setName(input);
                            redraw();
                            gui.show(itemPropertyGui.getPlayer());
                        }
                        return Prompt.END_OF_CONVERSATION;
                    }
                };

                gui.close(itemPropertyGui.getPlayer());
                new ConversationFactory(itemPropertyGui.getPlugin())
                        .withFirstPrompt(namePrompt)
                        .withTimeout(60)
                        .withLocalEcho(false)
                        .buildConversation(itemPropertyGui.getPlayer())
                        .begin();
                return true;
            });
        }));

        gui.addElement(new DynamicGuiElement('t', () -> {
            ItemStack typeItem = new ItemStack(Material.BOOK);
            ItemMeta meta = typeItem.getItemMeta();
            assert meta != null;

            meta.setDisplayName(ColorUtil.string("&6&lType"));
            List<String> lore = List.of(
                    ColorUtil.string("&7Click to change")
            );
            if (type == null) {
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            meta.setLore(lore.stream()
                             .map(ColorUtil::string)
                             .toList());

            typeItem.setItemMeta(meta);

            return new StaticGuiElement('t', typeItem, (action) -> {
                new TypeInputGui(itemPropertyGui.getPlugin(), this).getGui().show(itemPropertyGui.getPlayer());
                return true;
            }); // TODO: Open type input list gui
        }));

        gui.addElement(new DynamicGuiElement('v', () -> {
            ItemStack valueItem = new ItemStack(Material.OAK_SIGN);
            ItemMeta meta = valueItem.getItemMeta();
            assert meta != null;

            meta.setDisplayName(ColorUtil.string("&6&lValue"));
            List<String> lore = List.of(
                    ColorUtil.string("&7Click to change")
            );
            if (value == null) {
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            meta.setLore(lore);

            valueItem.setItemMeta(meta);

            return new StaticGuiElement('v', valueItem); // TODO: Open value input gui
        }));

        // TODO: Add property preview
        gui.addElement(new DynamicGuiElement('p', () -> {
            if (property == null) {
                ItemStack noPreviewItem = new ItemStack(Material.BARRIER);
                ItemMeta meta = noPreviewItem.getItemMeta();
                assert meta != null;

                meta.setDisplayName(ColorUtil.string("&c&lNo Preview"));
                List<String> lore = List.of(
                        "",
                        ColorUtil.string("&7Name, type and value must be set!")
                );
                meta.setLore(lore);

                noPreviewItem.setItemMeta(meta);

                return new StaticGuiElement('p', noPreviewItem);
            } else {
                return property.bakeElement('p');
            }
        }));

        gui.addElement(new StaticGuiElement('x', FILLER));

        return gui;
    }

    void redraw() {
        gui.draw(itemPropertyGui.getPlayer(), true, false);
    }

    public Player getPlayer() {
        return itemPropertyGui.getPlayer();
    }

    public InventoryGui getGui() {
        return gui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
