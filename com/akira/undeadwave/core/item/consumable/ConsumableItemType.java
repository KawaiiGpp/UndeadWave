package com.akira.undeadwave.core.item.consumable;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.Sound;

public enum ConsumableItemType {
    ENERGY_STEAK(Material.COOKED_BEEF, "能量牛排", false, 30, 400),
    HEALING_APPLE(Material.APPLE, "治愈苹果", false, 10, 600),
    ULTIMATE_HEALING_APPLE(Material.APPLE, "增强治愈苹果", true, 20, 1500),
    SPEED_SOUP(Material.RABBIT_STEW, "速度汤", false, 40, 600),

    FROZEN_FEATHER(Material.FEATHER, "冰冻羽毛", true, 200, new Tuple<>(Sound.BLOCK_GRASS_PLACE, 0.5F), 1800),
    FROZEN_MUSHROOM(Material.BROWN_MUSHROOM, "寒冰蘑菇", true, 400, new Tuple<>(Sound.BLOCK_GLASS_BREAK, 0.5F), 1800),

    GOLD_ARMOR(Material.GOLDEN_HELMET, "金盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_GOLD, 1.0F), 300),
    IRON_ARMOR(Material.IRON_HELMET, "铁盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_IRON, 1.0F), 2800),
    DIAMOND_ARMOR(Material.DIAMOND_HELMET, "钻石盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F), 5400);

    private final Material material;
    private final String displayName;
    private final boolean shiny;
    private final long cooldownTicks;
    private final Tuple<Sound, Float> sound;
    private final int cost;

    ConsumableItemType(Material material, String displayName, boolean shiny,
                       int cooldownSeconds, Tuple<Sound, Float> sound, int cost) {
        Validate.notNull(material);
        Validate.notNull(displayName);
        NumberUtils.ensureNonNegative(cooldownSeconds);
        Validate.notNull(sound);
        NumberUtils.ensurePositive(cost);

        this.material = material;
        this.displayName = displayName;
        this.shiny = shiny;
        this.cooldownTicks = cooldownSeconds * 20L;
        this.sound = sound;
        this.cost = cost;
    }

    ConsumableItemType(Material material, String displayName,
                       boolean shiny, int cooldownSeconds, int cost) {
        this(material, displayName, shiny, cooldownSeconds,
                new Tuple<>(Sound.ENTITY_GENERIC_EAT, 1.0F), cost);
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

    public int getCost() {
        return cost;
    }
}
