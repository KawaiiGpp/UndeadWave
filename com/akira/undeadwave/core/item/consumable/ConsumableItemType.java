package com.akira.undeadwave.core.item.consumable;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.Sound;

public enum ConsumableItemType {
    ENERGY_STEAK(Material.COOKED_BEEF, "能量牛排", false, 100),
    HEALING_APPLE(Material.APPLE, "治愈苹果", false, 100),
    ULTIMATE_HEALING_APPLE(Material.APPLE, "增强治愈苹果", true, 200),
    FROZEN_FEATHER(Material.FEATHER, "冰冻羽毛", false, 200),
    FROZEN_MUSHROOM(Material.BROWN_MUSHROOM, "寒冰蘑菇", true, 400),

    LEATHER_ARMOR(Material.LEATHER_HELMET, "皮革盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_LEATHER, 1.0F)),
    IRON_ARMOR(Material.IRON_HELMET, "铁盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_IRON, 1.0F)),
    DIAMOND_ARMOR(Material.DIAMOND_HELMET, "钻石盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F));

    private final Material material;
    private final String displayName;
    private final boolean shiny;
    private final long cooldownTicks;
    private final Tuple<Sound, Float> sound;

    ConsumableItemType(Material material, String displayName, boolean shiny,
                       long cooldownTicks, Tuple<Sound, Float> sound) {
        Validate.notNull(material);
        Validate.notNull(displayName);
        NumberUtils.ensureNonNegative(cooldownTicks);
        Validate.notNull(sound);

        this.material = material;
        this.displayName = displayName;
        this.shiny = shiny;
        this.cooldownTicks = cooldownTicks;
        this.sound = sound;
    }

    ConsumableItemType(Material material, String displayName,
                       boolean shiny, long cooldownTicks) {
        this(material, displayName, shiny, cooldownTicks,
                new Tuple<>(Sound.ENTITY_GENERIC_EAT, 1.0F));
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isShiny() {
        return shiny;
    }

    public long getCooldownTicks() {
        return cooldownTicks;
    }

    public Sound getSound() {
        return sound.getNonNullA();
    }

    public float getSoundPitch() {
        Float f = sound.getNonNullB();
        return CommonUtils.requireNonNull(f);
    }
}
