package com.github.kaspiandev.nbtgui.command;

public enum SubCommands {

    ITEM("item", "nbtgui.command.item"),
    ADD("add", "nbtgui.command.add");

    private final String key;
    private final String permission;

    SubCommands(String key, String permission) {
        this.key = key;
        this.permission = permission;
    }

    public String getKey() {
        return key;
    }

    public String getPermission() {
        return permission;
    }

}
