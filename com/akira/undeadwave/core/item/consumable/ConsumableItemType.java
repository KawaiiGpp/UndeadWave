package com.akira.undeadwave.core.item.consumable;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.NumberUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.Sound;

public enum ConsumableItemType {
    ENERGY_STEAK(Material.COOKED_BEEF, "能量牛排", false, 100, 100),
    HEALING_APPLE(Material.APPLE, "治愈苹果", false, 100, 100),
    ULTIMATE_HEALING_APPLE(Material.APPLE, "增强治愈苹果", true, 200, 250),
    SPEED_SOUP(Material.RABBIT_STEW, "速度汤", false, 200, 175),

    FROZEN_FEATHER(Material.FEATHER, "冰冻羽毛", true, 200, new Tuple<>(Sound.BLOCK_GRASS_PLACE, 0.5F), 400),
    FROZEN_MUSHROOM(Material.BROWN_MUSHROOM, "寒冰蘑菇", true, 400, new Tuple<>(Sound.BLOCK_GLASS_BREAK, 0.5F), 400),

    LEATHER_ARMOR(Material.LEATHER_HELMET, "皮革盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_LEATHER, 1.0F), 150),
    IRON_ARMOR(Material.IRON_HELMET, "铁盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_IRON, 1.0F), 375),
    DIAMOND_ARMOR(Material.DIAMOND_HELMET, "钻石盔甲套装", false, 0, new Tuple<>(Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F), 500);

    private final Material material;
    private final String displayName;
    private final boolean shiny;
    private final long cooldownTicks;
    private final Tuple<Sound, Float> sound;
    private final int cost;

    ConsumableItemType(Material material, String displayName, boolean shiny,
                       long cooldownTicks, Tuple<Sound, Float> sound, int cost) {
        Validate.notNull(material);
        Validate.notNull(displayName);
        NumberUtils.ensureNonNegative(cooldownTicks);
        Validate.notNull(sound);
        NumberUtils.ensurePositive(cost);

        this.material = material;
        this.displayName = displayName;
        this.shiny = shiny;
        this.cooldownTicks = cooldownTicks;
        this.sound = sound;
        this.cost = cost;
    }

    ConsumableItemType(Material material, String displayName,
                       boolean shiny, long cooldownTicks, int cost) {
        this(material, displayName, shiny, cooldownTicks,
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
