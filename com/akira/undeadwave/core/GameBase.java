package com.akira.undeadwave.core;

import com.akira.core.api.config.ConfigFile;
import com.akira.core.api.util.PlayerUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import com.akira.undeadwave.core.enemy.EnemyManager;
import com.akira.undeadwave.core.enemy.skeleton.CommonSkeleton;
import com.akira.undeadwave.core.enemy.zombie.CommonZombie;
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
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameBase {
    protected final UndeadWave plugin;

    protected final WeaponManager weaponManager;
    protected final EnemyManager enemyManager;

    protected GameSession session;
    protected GameState state;
    protected GameRound round;

    protected GameBase(UndeadWave plugin) {
        Validate.notNull(plugin);

        this.plugin = plugin;
        this.state = GameState.UNAVAILABLE;

        this.weaponManager = new WeaponManager();
        this.enemyManager = new EnemyManager();
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
        enemyManager.register(new CommonSkeleton(plugin));
    }

    public final Player getIngamePlayer() {
        validateState(GameState.STARTED);

        return this.getSessionSafely().getOwner();
    }

    public final GameState getState() {
        Validate.notNull(state);
        return state;
    }

    public final GameSession getSessionSnapshot() {
        return this.getSessionSafely().copy();
    }

    public final WeaponManager getWeaponManager() {
        return weaponManager;
    }

    public final EnemyManager getEnemyManager() {
        return enemyManager;
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

    protected final GameSession getSessionSafely() {
        Validate.notNull(session, "Session is currently null.");
        return session;
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
