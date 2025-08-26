package com.akira.undeadwave.listener;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.weapon.*;
import com.akira.undeadwave.core.weapon.tool.MeleeAttackData;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class WeaponListener extends ListenerBase {
    public WeaponListener(UndeadWave plugin) {
        super(plugin);
    }

    @EventHandler
    public void onMelee(EntityDamageByEntityEvent e) {
        DamageCause cause = e.getCause();
        if (cause != DamageCause.ENTITY_ATTACK && cause != DamageCause.ENTITY_SWEEP_ATTACK) return;

        if (!(e.getDamager() instanceof Player player)) return;
        if (!(e.getEntity() instanceof LivingEntity livingEntity)) return;
        if (!this.isIngamePlayer(player)) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        Weapon weapon = parseItem(item, WeaponAttackType.MELEE, MeleeWeapon.class);
        if (weapon == null) return;

        boolean sweep = cause == DamageCause.ENTITY_SWEEP_ATTACK;
        MeleeAttackData data = new MeleeAttackData(player, livingEntity, sweep);
        MeleeWeapon meleeWeapon = (MeleeWeapon) weapon;

        meleeWeapon.onAttack(data);
        this.applyMeleeAttackData(e, data);
    }

    @EventHandler
    public void onMelee(PlayerItemBreakEvent e) {
        Player player = e.getPlayer();
        if (!this.isIngamePlayer(player)) return;

        ItemStack item = e.getBrokenItem();
        Weapon weapon = parseItem(item, WeaponAttackType.MELEE, MeleeWeapon.class);
        if (weapon == null) return;

        player.sendMessage(weapon.getItemDestroyedMessage());
    }

    @EventHandler
    public void onRanged(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!this.isIngamePlayer(player)) return;

        ItemStack item = e.getItem();
        Weapon weapon = parseItem(item, WeaponAttackType.RANGED, RangedWeapon.class);

        if (weapon == null) return;
        e.setCancelled(true);

        Action action = e.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        RangedWeapon rangedWeapon = (RangedWeapon) weapon;
        rangedWeapon.onShoot(player, e.getItem(), e.getHand());
    }

    @EventHandler
    public void onRanged(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity livingEntity)) return;
        if (!(e.getDamager() instanceof Player player)) return;

        if (!this.isIngamePlayer(player)) return;
        if (livingEntity.hasMetadata("ingame.ranged_weapon.targeted")) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        Weapon weapon = parseItem(item, WeaponAttackType.RANGED, RangedWeapon.class);
        if (weapon == null) return;

        e.setCancelled(true);
        player.sendMessage("§c枪械类武器只能用于远程射击。");
    }

    private void applyMeleeAttackData(EntityDamageByEntityEvent e, MeleeAttackData data) {
        Validate.notNull(e);
        Validate.notNull(data);

        LivingEntity livingEntity = (LivingEntity) e.getEntity();

        if (data.shouldBeCancelled()) {
            e.setCancelled(true);
            return;
        }

        if (!data.isTrueDamage()) {
            e.setDamage(data.getDamage());
            return;
        }

        double damage = data.getDamage();
        double health = livingEntity.getHealth();

        if (damage >= health) {
            e.setDamage(Long.MAX_VALUE);
            return;
        }

        livingEntity.setHealth(health - damage);
        e.setDamage(0);
    }

    private <T extends Weapon> Weapon parseItem(ItemStack item, WeaponAttackType type, Class<T> clz) {
        Validate.notNull(type);
        Validate.notNull(clz);
        if (item == null) return null;

        Game game = plugin.getGame();
        WeaponManager manager = game.getWeaponManager();
        Weapon weapon = manager.fromItemStack(item);

        if (weapon == null) return null;
        if (weapon.getAttackType() != type) return null;
        if (!clz.isAssignableFrom(weapon.getClass())) return null;

        return weapon;
    }
}
