package com.github.kaspiandev.nbtgui.command.subcommand;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.command.SubCommand;
import com.github.kaspiandev.nbtgui.command.SubCommands;
import com.github.kaspiandev.nbtgui.gui.ItemPropertyGui;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemSubcommand extends SubCommand {

    private final NBTGui plugin;

    public ItemSubcommand(NBTGui plugin) {
        super(SubCommands.ITEM, plugin.getDocument().getString("no-perms"));
        this.plugin = plugin;
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.only-player")));
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.no-item")));
            return;
        }

        new ItemPropertyGui(plugin).open(player, item);
    }

    @Override
    public List<String> suggestions(CommandSender sender, String[] args) {
        return List.of();
    }

}
