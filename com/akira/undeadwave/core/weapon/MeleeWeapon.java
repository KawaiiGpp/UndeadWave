package com.akira.undeadwave.core.weapon;

import com.akira.core.api.item.ItemTagEditor;
import com.akira.core.api.util.*;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.GameState;
import com.akira.undeadwave.core.weapon.tool.MeleeAttackData;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class MeleeWeapon extends Weapon {
    private final boolean trueDamage;
    private final boolean allowSweep;

    private final float velocityMultiplier;
    private final int sweepDamageBonus;
    private final double sweepDamageModifier;

    private final int lifeStealChance;
    private final double lifeStealRatio;

    public MeleeWeapon(UndeadWave plugin,
                       WeaponType weaponType, WeaponAttackType attackType,
                       Material material, String displayName, String[] description,
                       double damage, int critDamage, int critChance,
                       boolean trueDamage, boolean allowSweep,
                       float velocityMultiplier, int sweepDamageBonus,
                       int lifeStealChance, double lifeStealRatio) {
        super(plugin, weaponType, attackType,
                material, displayName, description,
                damage, critDamage, critChance);
        NumberUtils.ensureNonNegative(velocityMultiplier);
        NumberUtils.ensureNonNegative(sweepDamageBonus);
        NumberUtils.ensureNonNegative(lifeStealChance);
        NumberUtils.ensureNonNegative(lifeStealRatio);
        Validate.isTrue(lifeStealChance >= 0 && lifeStealChance <= 100, "Life steal chance is out of range.");

        this.trueDamage = trueDamage;
        this.allowSweep = allowSweep;
        this.velocityMultiplier = velocityMultiplier;
        this.sweepDamageBonus = sweepDamageBonus;
        this.sweepDamageModifier = 0.25;
        this.lifeStealChance = lifeStealChance;
        this.lifeStealRatio = lifeStealRatio;
    }

    public final void onAttack(MeleeAttackData data) {
        Validate.notNull(data);

        Player player = data.getAttacker();
        if (handleAttackCooldown(player, data)) return;

        LivingEntity victim = data.getVictim();
        boolean crit = rollCritChance();
        double damageDealt = crit ? calculateCritDamage() : damage;

        if (handleAttack(damageDealt, data)) return;
        if (crit) handleCritNotification(player, victim);
        handleLifeSteal(damageDealt, player);
        handleKnockback(data);
    }

    public final boolean isTrueDamage() {
        return trueDamage;
    }

    public final boolean isAllowSweep() {
        return allowSweep;
    }

    public final float getVelocityMultiplier() {
        return velocityMultiplier;
    }

    public final float getSweepDamageBonus() {
        return sweepDamageBonus;
    }

    public final int getLifeStealChance() {
        return lifeStealChance;
    }

    public final double getLifeStealRatio() {
        return lifeStealRatio;
    }

    public final boolean isLifeStealEnabled() {
        return lifeStealChance > 0 && lifeStealRatio > 0;
    }

    protected final boolean rollLifeStealChance() {
        return CommonUtils.rollChance(lifeStealChance);
    }

    protected final double calculateLifeSteal(double damageDealt) {
        NumberUtils.ensurePositive(damageDealt);
        return damageDealt * (lifeStealRatio / 100);
    }

    protected List<String> onItemStatsAppend() {
        List<String> list = new ArrayList<>();

        list.add("§f总耐久：§b" + material.getMaxDurability());

        if (velocityMultiplier != 1.0)
            list.add("§f击退距离：§e" + NumberUtils.format(velocityMultiplier) + "x");
        if (allowSweep && sweepDamageBonus > 0)
            list.add("§f横扫加成：§a+" + sweepDamageBonus + "%");

        return list;
    }

    protected List<String> onItemDescriptionAppend() {
        List<String> list = new ArrayList<>();

        if (trueDamage)
            list.add("§4❂ §c造成真实伤害 §4❂");
        if (allowSweep)
            list.add("§3⚔ §b造成横扫伤害 §3⚔");

        if (this.isLifeStealEnabled()) {
            if (!list.isEmpty()) list.add("");

            list.add("§5❣ §d生命窃取 §5❣");
            list.add("§f每次攻击有 §a" + lifeStealChance + "% §f的概率，");
            list.add("§f窃取到 §6" + NumberUtils.format(lifeStealRatio) + "% §f于");
            list.add("§f实际攻击伤害的生命值。");
        }

        return list;
    }

    protected void onItemTagAppend(ItemTagEditor editor) {}

    private boolean handleAttackCooldown(Player player, MeleeAttackData data) {
        Validate.notNull(player);
        Validate.notNull(data);

        if (data.isSweep()) return false;

        if (player.getAttackCooldown() < 0.85F) {
            data.setCancel(true);
            player.sendMessage("§c武器冷却完毕后才能进行攻击。");
            return true;
        } else return false;
    }

    private void handleKnockback(MeleeAttackData data) {
        Validate.notNull(data);
        LivingEntity entity = data.getVictim();

        Bukkit.getScheduler().runTask(plugin, () -> {
            if (game.getState() != GameState.STARTED) return;
            if (!entity.isValid()) return;

            Vector velocity = entity.getVelocity().clone();
            entity.setVelocity(velocity.multiply(velocityMultiplier).setY(0));
        });
    }

    private void handleLifeSteal(double damage, Player player) {
        NumberUtils.ensurePositive(damage);
        Validate.notNull(player);

        if (!rollLifeStealChance()) return;

        double healing = calculateLifeSteal(damage);
        double health = player.getHealth();
        double maxHealth = EntityUtils.getMaxHealth(player);

        player.setHealth(Math.min(health + healing, maxHealth));
        player.sendMessage("§f你刚刚窃取了 §d" + NumberUtils.format(healing) + "♥§f。");
        WorldUtils.playParticle(player.getEyeLocation(), Particle.HEART, 3, 0.5);
        PlayerUtils.playSound(player, Sound.ENTITY_ITEM_PICKUP, 2F);
    }

    private boolean handleAttack(double damageDealt, MeleeAttackData data) {
        NumberUtils.ensurePositive(damageDealt);
        Validate.notNull(data);

        boolean sweep = data.isSweep();

        if (sweep && !allowSweep) {
            data.setCancel(true);
            return true;
        }

        data.setDamage(sweep ? calculateSweepDamage(damageDealt) : damageDealt);
        data.setTrueDamage(trueDamage);
        return false;
    }

    private void handleCritNotification(Player player, LivingEntity victim) {
        Validate.notNull(player);
        Validate.notNull(victim);

        BlockData blockData = Material.REDSTONE_BLOCK.createBlockData();
        WorldUtils.playParticle(victim.getEyeLocation(), Particle.BLOCK_CRACK, 10, 0.5, blockData);

        PlayerUtils.playSound(player, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.25F, 1.0F);
    }

    private double calculateSweepDamage(double damage) {
        NumberUtils.ensurePositive(damage);

        double baseSweepDamage = damage * sweepDamageModifier;
        double multiplier = 1 + (sweepDamageBonus / 100F);
        return baseSweepDamage * multiplier;
    }
}
