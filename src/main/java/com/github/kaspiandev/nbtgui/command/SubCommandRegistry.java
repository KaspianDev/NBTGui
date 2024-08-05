package com.github.kaspiandev.nbtgui.command;

import com.github.kaspiandev.nbtgui.NBTGui;
import com.github.kaspiandev.nbtgui.command.subcommand.ItemSubcommand;

import java.util.HashMap;
import java.util.Map;

public class SubCommandRegistry {

    private final NBTGui plugin;
    private final Map<String, SubCommand> registry;

    public SubCommandRegistry(NBTGui plugin) {
        this.plugin = plugin;
        this.registry = new HashMap<>();
        load();
    }

    private void load() {
        ItemSubcommand itemSubcommand = new ItemSubcommand(plugin);
        registry.put(itemSubcommand.getType().getKey(), itemSubcommand);
    }

    public Map<String, SubCommand> getRegistry() {
        return registry;
    }

    public SubCommand findById(String id) {
        return registry.get(id);
    }

}
