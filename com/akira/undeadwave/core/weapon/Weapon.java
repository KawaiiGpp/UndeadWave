package com.akira.undeadwave.core.weapon;

import com.akira.core.api.item.ItemBuilder;
import com.akira.core.api.item.ItemTagEditor;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.NumberUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Weapon {
    protected final UndeadWave plugin;
    protected final Game game;

    protected final WeaponType weaponType;
    protected final WeaponAttackType attackType;

    protected final Material material;
    protected final String displayName;
    protected final String[] description;

    protected final double damage;
    protected final int critDamage;
    protected final int critChance;

    public Weapon(UndeadWave plugin, Game game,
                  WeaponType weaponType, WeaponAttackType attackType,
                  Material material, String displayName, String[] description,
                  double damage, int critDamage, int critChance) {
        Validate.notNull(plugin);
        Validate.notNull(game);
        Validate.notNull(weaponType);
        Validate.notNull(attackType);
        Validate.notNull(material);
        Validate.notNull(displayName);
        Validate.noNullElements(description);
        NumberUtils.ensurePositive(damage);
        NumberUtils.ensureNonNegative(critDamage);
        NumberUtils.ensureNonNegative(critChance);
        Validate.isTrue(critChance >= 0 && critChance <= 100, "Crit chance is out of range.");

        this.plugin = plugin;
        this.game = game;
        this.weaponType = weaponType;
        this.attackType = attackType;
        this.material = material;
        this.displayName = displayName;
        this.description = description;
        this.damage = damage;
        this.critDamage = critDamage;
        this.critChance = critChance;
    }

    public final ItemStack buildItem() {
        ItemStack item = ItemBuilder.create(material)
                .addFlags(ItemFlag.values())
                .setLore(generateLore())
                .setDisplayName(attackType.getColor() + displayName)
                .getResult();

        ItemTagEditor editor = ItemTagEditor.forItemMeta(plugin, item);
        editor.set("ingame.weapon", PersistentDataType.STRING, weaponType.name());
        this.onItemTagAppend(editor);

        editor.apply(item);
        return item;
    }

    public final boolean matchesItem(ItemStack item) {
        Validate.notNull(item);
        String keyName = "ingame.weapon";

        ItemTagEditor editor = ItemTagEditor.forItemMeta(plugin, item);
        if (!editor.hasKey(keyName, PersistentDataType.STRING)) return false;

        String name = editor.get(keyName, PersistentDataType.STRING);
        WeaponType type = CommonUtils.getEnumSafely(WeaponType.class, name);
        Validate.notNull(type, "Unknown weapon type from Item tag: " + name);

        return this.weaponType == type;
    }

    public final String getItemDestroyedMessage() {
        return "§c物品损毁！§f你的武器 " + attackType.getColor() + displayName + " §f耐久已经耗尽。";
    }

    public final WeaponType getWeaponType() {
        return weaponType;
    }

    public final WeaponAttackType getAttackType() {
        return attackType;
    }

    public final Material getMaterial() {
        return material;
    }

    public final String getDisplayName() {
        return displayName;
    }

    public final String[] getDescription() {
        return description.clone();
    }

    public final double getDamage() {
        return damage;
    }

    public final int getCritDamage() {
        return critDamage;
    }

    public final int getCritChance() {
        return critChance;
    }

    protected final boolean rollCritChance() {
        return CommonUtils.rollChance(critChance);
    }

    protected final double calculateCritDamage() {
        return damage * (1 + (critDamage / 100F));
    }

    private String[] generateLore() {
        List<String> lore = new ArrayList<>();

        lore.add("§f伤害：§c" + NumberUtils.format(damage) + "♥");
        if (critChance > 0 && critDamage > 0) {
            lore.add("§f暴击率：§a+" + critChance + "%");
            lore.add("§f暴击伤害：§a+" + critDamage + "%");
        }
        lore.addAll(this.onItemStatsAppend());
        lore.add("");

        Arrays.stream(description).forEach(line -> lore.add("§7" + line));
        lore.add("");

        List<String> additionalDesc = this.onItemDescriptionAppend();
        if (!additionalDesc.isEmpty()) {
            lore.addAll(additionalDesc);
            lore.add("");
        }

        lore.add(attackType.getColor() + attackType.getDisplayName() + "武器 " + attackType.getUsage());
        return lore.toArray(String[]::new);
    }

    protected abstract List<String> onItemStatsAppend();

    protected abstract List<String> onItemDescriptionAppend();

    protected abstract void onItemTagAppend(ItemTagEditor editor);
}
