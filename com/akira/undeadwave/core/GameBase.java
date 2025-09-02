package com.akira.undeadwave.core;

import com.akira.core.api.config.ConfigFile;
import com.akira.core.api.util.PlayerUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import com.akira.undeadwave.core.enemy.EnemyManager;
import com.akira.undeadwave.core.enemy.skeleton.other.ImpactSkeleton;
import com.akira.undeadwave.core.enemy.skeleton.other.SniperSkeleton;
import com.akira.undeadwave.core.enemy.skeleton.regular.CommonSkeleton;
import com.akira.undeadwave.core.enemy.skeleton.regular.IronSkeleton;
import com.akira.undeadwave.core.enemy.skeleton.regular.GoldSkeleton;
import com.akira.undeadwave.core.enemy.zombie.damage.ShadowAssassinZombie;
import com.akira.undeadwave.core.enemy.zombie.damage.ThunderblotZombie;
import com.akira.undeadwave.core.enemy.zombie.regular.CommonZombie;
import com.akira.undeadwave.core.enemy.zombie.regular.DiamondZombie;
import com.akira.undeadwave.core.enemy.zombie.regular.GoldZombie;
import com.akira.undeadwave.core.enemy.zombie.regular.IronZombie;
import com.akira.undeadwave.core.enemy.zombie.speed.LightningZombie;
import com.akira.undeadwave.core.enemy.zombie.speed.SpeedsterZombie;
import com.akira.undeadwave.core.enemy.zombie.tank.RockZombie;
import com.akira.undeadwave.core.enemy.zombie.tank.TankZombie;
import com.akira.undeadwave.core.item.consumable.ConsumableItemManager;
import com.akira.undeadwave.core.item.consumable.ability.*;
import com.akira.undeadwave.core.item.consumable.armor.DiamondArmor;
import com.akira.undeadwave.core.item.consumable.armor.IronArmor;
import com.akira.undeadwave.core.item.consumable.armor.LeatherArmor;
import com.akira.undeadwave.core.item.weapon.WeaponManager;
import com.akira.undeadwave.core.item.weapon.melee.blood.BloodyBlade;
import com.akira.undeadwave.core.item.weapon.melee.blood.VampiricBlade;
import com.akira.undeadwave.core.item.weapon.melee.luck.FateSword;
import com.akira.undeadwave.core.item.weapon.melee.luck.LuckySword;
import com.akira.undeadwave.core.item.weapon.melee.regular.DiamondKnife;
import com.akira.undeadwave.core.item.weapon.melee.regular.IronKnife;
import com.akira.undeadwave.core.item.weapon.melee.regular.WoodKnife;
import com.akira.undeadwave.core.item.weapon.melee.shock.ShockBlade;
import com.akira.undeadwave.core.item.weapon.melee.shock.StormBlade;
import com.akira.undeadwave.core.item.weapon.melee.sweep.LongSword;
import com.akira.undeadwave.core.item.weapon.melee.sweep.WhirlwindSword;
import com.akira.undeadwave.core.item.weapon.melee.sweep.WindSword;
import com.akira.undeadwave.core.item.weapon.melee.truth.PiercingKnife;
import com.akira.undeadwave.core.item.weapon.melee.truth.TrueKnife;
import com.akira.undeadwave.core.item.weapon.melee.truth.TruthSword;
import com.akira.undeadwave.core.item.weapon.ranged.launcher.fire.FireLauncher;
import com.akira.undeadwave.core.item.weapon.ranged.launcher.fire.LavaLauncher;
import com.akira.undeadwave.core.item.weapon.ranged.launcher.rocket.AncientRocketLauncher;
import com.akira.undeadwave.core.item.weapon.ranged.launcher.rocket.RocketLauncher;
import com.akira.undeadwave.core.item.weapon.ranged.luck.FateGun;
import com.akira.undeadwave.core.item.weapon.ranged.luck.GamblerGun;
import com.akira.undeadwave.core.item.weapon.ranged.miner.CopperMiner;
import com.akira.undeadwave.core.item.weapon.ranged.miner.GoldMiner;
import com.akira.undeadwave.core.item.weapon.ranged.miner.NetheriteMiner;
import com.akira.undeadwave.core.item.weapon.ranged.pistol.GoldPistol;
import com.akira.undeadwave.core.item.weapon.ranged.pistol.Pistol;
import com.akira.undeadwave.core.item.weapon.ranged.shotgun.DiamondShotgun;
import com.akira.undeadwave.core.item.weapon.ranged.shotgun.Shotgun;
import com.akira.undeadwave.core.item.weapon.ranged.sniper.InfernalSniper;
import com.akira.undeadwave.core.item.weapon.ranged.sniper.Sniper;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class GameBase {
    protected final UndeadWave plugin;

    protected final WeaponManager weaponManager;
    protected final EnemyManager enemyManager;
    protected final ConsumableItemManager consumableItemManager;

    protected GameSession session;
    protected GameState state;
    protected BukkitTask infoBarLoop;

    protected GameBase(UndeadWave plugin) {
        Validate.notNull(plugin);

        this.plugin = plugin;
        this.state = GameState.UNAVAILABLE;

        this.weaponManager = new WeaponManager();
        this.enemyManager = new EnemyManager();
        this.consumableItemManager = new ConsumableItemManager();
    }

    public final void startInfoBarLoop() {
        Validate.isTrue(infoBarLoop == null, "Info bar loop already existing.");
        validateState(GameState.STARTED);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        GameInfoBar runnable = new GameInfoBar(session);

        infoBarLoop = scheduler.runTaskTimer(plugin, runnable, 0, 10);
    }

    public final void stopInfoBarLoop() {
        Validate.notNull(infoBarLoop, "Info bar loop not found.");

        infoBarLoop.cancel();
        infoBarLoop = null;
    }

    public final List<String> tryEnable() {
        LocationConfig config = this.getLocationConfig();
        List<String> tips = new ArrayList<>();

        if (config.getMonsterPoints().isEmpty())
            tips.add("至少需要设置一个怪物刷新点。");
        if (config.getLobby() == null)
            tips.add("需要设置大厅位置。");
        if (config.getSpawnpoint() == null)
            tips.add("需要设置出生点位置。");

        if (tips.isEmpty()) state = GameState.WAITING;
        return tips;
    }

    public final void disbale() {
        Validate.isTrue(state.allowDisabling(),
                "Disabling now allowed in state: " + state.name());
        state = GameState.UNAVAILABLE;
    }

    public final void initializeWeapons() {
        weaponManager.register(new WoodKnife(plugin));
        weaponManager.register(new IronKnife(plugin));
        weaponManager.register(new DiamondKnife(plugin));

        weaponManager.register(new BloodyBlade(plugin));
        weaponManager.register(new VampiricBlade(plugin));

        weaponManager.register(new LuckySword(plugin));
        weaponManager.register(new FateSword(plugin));

        weaponManager.register(new LongSword(plugin));
        weaponManager.register(new WindSword(plugin));
        weaponManager.register(new WhirlwindSword(plugin));

        weaponManager.register(new ShockBlade(plugin));
        weaponManager.register(new StormBlade(plugin));

        weaponManager.register(new PiercingKnife(plugin));
        weaponManager.register(new TrueKnife(plugin));
        weaponManager.register(new TruthSword(plugin));


        weaponManager.register(new Pistol(plugin));
        weaponManager.register(new GoldPistol(plugin));

        weaponManager.register(new Shotgun(plugin));
        weaponManager.register(new DiamondShotgun(plugin));

        weaponManager.register(new FireLauncher(plugin));
        weaponManager.register(new LavaLauncher(plugin));

        weaponManager.register(new GamblerGun(plugin));
        weaponManager.register(new FateGun(plugin));

        weaponManager.register(new CopperMiner(plugin));
        weaponManager.register(new GoldMiner(plugin));
        weaponManager.register(new NetheriteMiner(plugin));

        weaponManager.register(new RocketLauncher(plugin));
        weaponManager.register(new AncientRocketLauncher(plugin));

        weaponManager.register(new Sniper(plugin));
        weaponManager.register(new InfernalSniper(plugin));
    }

    public final void initializeEnemies() {
        enemyManager.register(new CommonZombie(plugin));
        enemyManager.register(new GoldZombie(plugin));
        enemyManager.register(new IronZombie(plugin));
        enemyManager.register(new DiamondZombie(plugin));

        enemyManager.register(new SpeedsterZombie(plugin));
        enemyManager.register(new LightningZombie(plugin));

        enemyManager.register(new ThunderblotZombie(plugin));
        enemyManager.register(new ShadowAssassinZombie(plugin));

        enemyManager.register(new RockZombie(plugin));
        enemyManager.register(new TankZombie(plugin));


        enemyManager.register(new CommonSkeleton(plugin));
        enemyManager.register(new GoldSkeleton(plugin));
        enemyManager.register(new IronSkeleton(plugin));

        enemyManager.register(new SniperSkeleton(plugin));
        enemyManager.register(new ImpactSkeleton(plugin));
    }

    public final void initializeConsumableItems() {
        consumableItemManager.register(new EnergySteak(plugin));
        consumableItemManager.register(new HealingApple(plugin));
        consumableItemManager.register(new UltimateHealingApple(plugin));
        consumableItemManager.register(new SpeedSoup(plugin));

        consumableItemManager.register(new FrozenFeather(plugin));
        consumableItemManager.register(new FrozenMushroom(plugin));

        consumableItemManager.register(new LeatherArmor(plugin));
        consumableItemManager.register(new IronArmor(plugin));
        consumableItemManager.register(new DiamondArmor(plugin));
    }

    public final GameState getState() {
        Validate.notNull(state);
        return state;
    }

    public final GameSession getSession() {
        Validate.notNull(session, "Session is currently null.");
        return session;
    }

    public final WeaponManager getWeaponManager() {
        return weaponManager;
    }

    public final EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public final ConsumableItemManager getConsumableItemManager() {
        return consumableItemManager;
    }

    protected final Player getIngamePlayer() {
        validateState(GameState.STARTED);

        return this.getSession().getOwner();
    }

    protected final ConfigFile getConfig(String name) {
        Validate.notNull(name);
        return plugin.getConfigManager().fromString(name);
    }

    protected final LocationConfig getLocationConfig() {
        return (LocationConfig) this.getConfig("location");
    }

    protected final SettingsConfig getSettingsConfig() {
        return (SettingsConfig) this.getConfig("settings");
    }

    protected final void validateState(GameState expected) {
        Validate.notNull(expected);

        String cur = state.name();
        String exp = expected.name();
        Validate.isTrue(state == expected, "Unexpected state: " + cur + " (" + exp + " expected)");
    }

    protected final void teleport(Location location) {
        Validate.notNull(location);

        this.getIngamePlayer().teleport(location);
    }

    protected final void send(String message) {
        Validate.notNull(message);

        this.getIngamePlayer().sendMessage(message);
    }

    protected final void playSound(Sound sound, float volume, float pitch) {
        PlayerUtils.playSound(this.getIngamePlayer(), sound, volume, pitch);
    }

    protected final void playSound(Sound sound, float pitch) {
        playSound(sound, 0.5F, pitch);
    }

    protected final void playSound(Sound sound) {
        playSound(sound, 1.0F);
    }
}
