package com.github.kaspiandev.nbtgui.command.subcommand;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.command.SubCommand;
import com.github.kaspiandev.nbtgui.command.SubCommands;
import com.github.kaspiandev.nbtgui.property.reader.PropertyFactory;
import com.github.kaspiandev.nbtgui.property.reader.PropertyWriter;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class AddSubcommand extends SubCommand {

    private final NBTGui plugin;

    public AddSubcommand(NBTGui plugin) {
        super(SubCommands.ADD, plugin.getDocument().getString("no-perms"));
        this.plugin = plugin;
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.only-player")));
            return;
        }

        if (args.length < 4) {
            sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.no-args")));
            return;
        }

        PlayerInventory inventory = player.getInventory();
        ItemStack item = inventory.getItemInMainHand();
        if (item.getType().isAir()) {
            sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.no-item")));
            return;
        }

        String key = args[1];
        PropertyFactory.findIndexedClass(args[2]).ifPresentOrElse((clazz) -> {
            try {
                // TODO: Implement adapters for primitive types
                System.out.println(clazz.getName());
                Object value = clazz.cast(args[3]);

                PropertyFactory.build(key, clazz, value).ifPresentOrElse((property) -> {
                    System.out.println(property.getName() + " " + property.getValue());
                    NBT.modify(item, (nbt) -> {
                        PropertyWriter.write(nbt, property);
                    });
                    sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.property-set")));
                    System.out.println("set");
                }, () -> sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.no-args"))));
            } catch (ClassCastException ex) {
                sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.cannot-parse")));
            }
        }, () -> sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.no-args"))));
    }

    @Override
    public List<String> suggestions(CommandSender sender, String[] args) {
        if (args.length == 3) {
            return new ArrayList<>(PropertyFactory.getIndexedClassNames());
        }
        return List.of();
    }

}
