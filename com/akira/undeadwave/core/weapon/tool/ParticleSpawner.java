package com.akira.undeadwave.core.weapon.tool;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;

@FunctionalInterface
public interface ParticleSpawner {
    void spawn(World world, Location location);

    default void run(Location location) {
        Validate.notNull(location);

        World world = location.getWorld();
        Validate.notNull(world);

        this.spawn(world, location);
    }
}
