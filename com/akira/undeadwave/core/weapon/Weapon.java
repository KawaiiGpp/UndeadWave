package com.akira.undeadwave.core.weapon;

import com.akira.core.api.item.ItemBuilder;
import com.akira.core.api.item.ItemTagEditor;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.NumberUtils;
import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Weapon {
    protected final WeaponType weaponType;
    protected final WeaponAttackType attackType;

    protected final Material material;
    protected final String displayName;
    protected final String[] description;

    protected final double damage;
    protected final int critDamage;
    protected final int critChance;

    protected final long cooldownTicks;
    protected final Particle particle;

    public Weapon(WeaponType weaponType, WeaponAttackType attackType,
                  Material material, String displayName, String[] description,
                  double damage, int critDamage, int critChance,
                  long cooldownTicks, Particle particle) {
        Validate.notNull(weaponType);
        Validate.notNull(attackType);
        Validate.notNull(material);
        Validate.notNull(displayName);
        Validate.notNull(particle);
        Validate.noNullElements(description);
        NumberUtils.ensurePositive(damage);
        NumberUtils.ensureNonNegative(critDamage);
        NumberUtils.ensureNonNegative(critChance);
        NumberUtils.ensurePositive(cooldownTicks);
        Validate.isTrue(critChance >= 0 && critChance <= 100, "Crit chance is out of range.");

        this.weaponType = weaponType;
        this.attackType = attackType;
        this.material = material;
        this.displayName = displayName;
        this.description = description;
        this.damage = damage;
        this.critDamage = critDamage;
        this.critChance = critChance;
        this.cooldownTicks = cooldownTicks;
        this.particle = particle;
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

    public final long getCooldownTicks() {
        return cooldownTicks;
    }

    public final Particle getParticle() {
        return particle;
    }

    public final ItemStack buildItem() {
        ItemStack item = ItemBuilder.create(material)
                .addFlags(ItemFlag.values())
                .setLore(generateLore())
                .setDisplayName(displayName)
                .getResult();

        ItemTagEditor editor = ItemTagEditor.forItemMeta(UndeadWave.getInstance(), item);
        editor.set("ingame.weapon", PersistentDataType.STRING, weaponType.name());
        editor.apply(item);

        return item;
    }

    public final boolean matchesItem(ItemStack item) {
        Validate.notNull(item);
        String keyName = "ingame.weapon";

        ItemTagEditor editor = ItemTagEditor.forItemMeta(UndeadWave.getInstance(), item);
        if (!editor.hasKey(keyName, PersistentDataType.STRING)) return false;

        String name = editor.get(keyName, PersistentDataType.STRING);
        WeaponType type = CommonUtils.getEnumSafely(WeaponType.class, name);
        Validate.notNull(type, "Unknown weapon type from Item tag: " + name);

        return this.weaponType == type;
    }

    private String[] generateLore() {
        List<String> lore = new ArrayList<>();

        lore.add("§f攻击伤害：§c" + NumberUtils.format(damage) + "♥");
        if (critChance > 0) {
            lore.add("§f暴击概率：§a" + critChance + "%");
            if (critDamage > 0)
                lore.add("§f暴击伤害：§a+" + critDamage + "%");
        }
        lore.add("§f冷却：§e" + NumberUtils.format(cooldownTicks / 20F) + "秒");
        lore.add("");

        Arrays.stream(description).forEach(line -> lore.add("§7" + line));
        lore.add("");

        lore.add(attackType.getColor() + attackType.getDisplayName() + " " + attackType.getUsage());
        return lore.toArray(String[]::new);
    }

    public abstract void onMeleeAttack(Player attacker, LivingEntity victim);

    public abstract void onRightClicked(Player player);
}
