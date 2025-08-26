package com.akira.undeadwave.core.item.weapon;

import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;

public enum WeaponAttackType {
    MELEE("近战", "左键攻击", ChatColor.GOLD),
    RANGED("远程", "右键发射", ChatColor.AQUA);

    private final String displayName;
    private final String usage;
    private final ChatColor color;

    WeaponAttackType(String displayName, String usage, ChatColor color) {
        Validate.notNull(displayName);
        Validate.notNull(usage);
        Validate.notNull(color);

        this.displayName = displayName;
        this.usage = usage;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsage() {
        return usage;
    }

    public ChatColor getColor() {
        return color;
    }
}
