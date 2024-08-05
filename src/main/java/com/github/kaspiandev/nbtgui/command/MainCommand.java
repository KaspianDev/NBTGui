package com.github.kaspiandev.nbtgui.command;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.util.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements TabExecutor {

    private final NBTGui plugin;
    private final SubCommandRegistry registry;

    public MainCommand(NBTGui plugin, SubCommandRegistry registry) {
        this.plugin = plugin;
        this.registry = registry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.no-args")));
            return false;
        } else {
            SubCommand cmd = registry.findById(args[0]);
            if (cmd == null) {
                sender.spigot().sendMessage(ColorUtil.component(plugin.getDocument().getString("message.unknown-arg")));
                return false;
            }
            cmd.checkPerms(sender, args);
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String label, @NotNull String[] args) {
        if (args.length <= 1) {
            return new ArrayList<>(registry.getRegistry().keySet());
        } else {
            SubCommand subCommand = registry.findById(args[0]);
            if (subCommand == null) return null;

            return subCommand.suggestions(sender, args);
        }
    }

}
