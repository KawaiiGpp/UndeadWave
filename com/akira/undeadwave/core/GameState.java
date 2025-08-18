package com.akira.undeadwave.core;

import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;

import java.util.Arrays;

public enum GameState {
    UNAVAILABLE(ChatColor.DARK_RED, "不可用"),
    WAITING(ChatColor.GREEN, "等待中"),
    STARTED(ChatColor.RED, "已开始"),
    ENDING(ChatColor.GOLD, "重置中");

    private final ChatColor color;
    private final String displayName;

    GameState(ChatColor color, String displayName) {
        Validate.notNull(color);
        Validate.notNull(displayName);

        this.color = color;
        this.displayName = displayName;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColoredName() {
        return color + displayName;
    }

    public boolean isAvailable() {
        return this != UNAVAILABLE;
    }

    public boolean isUnavailable() {
        return this == UNAVAILABLE;
    }

    public boolean allowDisabling() {
        return this == WAITING;
    }

    public boolean isIn(GameState... vars) {
        Validate.noNullElements(vars);
        return Arrays.asList(vars).contains(this);
    }
}
