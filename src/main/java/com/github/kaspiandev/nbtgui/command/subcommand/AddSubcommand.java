package com.github.kaspiandev.nbtgui.command.subcommand;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.command.SubCommand;
import com.github.kaspiandev.nbtgui.command.SubCommands;
import com.github.kaspiandev.nbtgui.property.registry.PropertyFactory;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
            PropertyFactory.build(key, clazz, args[3]).ifPresentOrElse((property) -> {
                NBT.modify(item, property::writeTo);
                sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.property-set")));
            }, () -> sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.cannot-parse"))));
        }, () -> sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.no-args"))));
    }

    @Override
    public List<String> suggestions(CommandSender sender, String[] args) {
        if (args.length == 3) {
            return PropertyFactory.getIndexedClassNames();
        }
        return List.of();
    }

}
