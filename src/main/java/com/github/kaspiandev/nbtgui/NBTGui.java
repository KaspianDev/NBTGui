package com.github.kaspiandev.nbtgui;

import com.github.kaspiandev.nbtgui.command.MainCommand;
import com.github.kaspiandev.nbtgui.command.SubCommandRegistry;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class NBTGui extends JavaPlugin {

    private YamlDocument document;

    @Override
    public void onEnable() {
        try {
            document = YamlDocument.create(
                    new File(getDataFolder(), "config.yml"),
                    Objects.requireNonNull(getResource("config.yml")),
                    GeneralSettings.builder().setUseDefaults(false).build(),
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("version")).build()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PluginCommand nbtGuiCommand = getCommand("nbtgui");
        if (nbtGuiCommand != null) {
            SubCommandRegistry subCommandRegistry = new SubCommandRegistry(this);
            MainCommand mainCommand = new MainCommand(this, subCommandRegistry);

            nbtGuiCommand.setExecutor(mainCommand);
            nbtGuiCommand.setTabCompleter(mainCommand);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public YamlDocument getDocument() {
        return document;
    }

}
