package com.akira.undeadwave.core.enemy;

import com.akira.core.api.tool.MetadataEditor;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.EntityUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.MetadataValue;

public abstract class Enemy<T extends Monster> {
    protected final UndeadWave plugin;
    protected final Game game;

    protected final EntityType entityType;
    protected final Class<T> typeClass;
    protected final EnemyType enemyType;

    public Enemy(UndeadWave plugin, EntityType entityType, Class<T> typeClass, EnemyType enemyType) {
        Validate.notNull(plugin);
        Validate.notNull(plugin.getGame());
        Validate.notNull(entityType);
        Validate.notNull(typeClass);
        Validate.notNull(enemyType);

        this.plugin = plugin;
        this.game = plugin.getGame();
        this.entityType = entityType;
        this.typeClass = typeClass;
        this.enemyType = enemyType;
    }

    public final T spawn(Location location) {
        Validate.notNull(location);

        World world = location.getWorld();
        Validate.notNull(world);

        T entity = world.spawn(location, typeClass);

        entity.setRemoveWhenFarAway(false);
        entity.setMaximumNoDamageTicks(0);
        MetadataEditor.create(plugin, entity).set("ingame.enemy", enemyType.name());

        this.applyEquipmentPreset(entity);
        this.applyAttribute(entity);
        this.doEntityPresets(entity);

        return entity;
    }

    public final boolean matches(Monster monster) {
        Validate.notNull(monster);
        if (!typeClass.isInstance(monster)) return false;

        String key = "ingame.enemy";
        MetadataEditor editor = MetadataEditor.create(plugin, monster);
        if (!editor.has(key)) return false;

        MetadataValue value = CommonUtils.requireNonNull(editor.get(key));
        EnemyType type = CommonUtils.getEnumSafely(EnemyType.class, value.asString());
        Validate.notNull(type, "Failed parsing enemy type: " + monster.getType().name());

        return type == enemyType;
    }

    public final EntityType getEntityType() {
        return entityType;
    }

    public final Class<T> getTypeClass() {
        return typeClass;
    }

    public final EnemyType getEnemyType() {
        return enemyType;
    }

    protected final void applyModifier(T entity, Attribute type, String name, double amount, Operation operation) {
        Validate.notNull(entity);
        Validate.notNull(type);
        Validate.notNull(name);
        Validate.notNull(operation);

        AttributeModifier modifier = EntityUtils.createModifier("undeadwave.enemy." + name, amount, operation);
        EntityUtils.addModifier(entity, type, modifier);
    }

    protected final void applyScalingModifier(T entity, Attribute type, String name, double amount) {
        applyModifier(entity, type, name, amount, Operation.ADD_SCALAR);
    }

    protected final void applyMultiplyModifier(T entity, Attribute type, String name, double amount) {
        applyModifier(entity, type, name, amount, Operation.MULTIPLY_SCALAR_1);
    }

    protected final void applyAddModifier(T entity, Attribute type, String name, double amount) {
        applyModifier(entity, type, name, amount, Operation.ADD_NUMBER);
    }

    protected abstract void doEntityPresets(T entity);

    protected abstract EnemyEquipmentPreset getEquipmentPreset();

    private void applyEquipmentPreset(T entity) {
        Validate.notNull(entity);

        EnemyEquipmentPreset preset = this.getEquipmentPreset();
        if (preset == null) return;

        EntityEquipment equipment = entity.getEquipment();
        Validate.notNull(equipment, "Entity not available for equipment: " + entityType);

        equipment.setItemInMainHand(preset.getWeapon());
        equipment.setArmorContents(preset.getArmorSet());

        equipment.setHelmetDropChance(0);
        equipment.setChestplateDropChance(0);
        equipment.setLeggingsDropChance(0);
        equipment.setBootsDropChance(0);
        equipment.setItemInMainHandDropChance(0);
        equipment.setItemInOffHandDropChance(0);
    }

    private void applyAttribute(T entity) {
        Validate.notNull(entity);

        applyScalingModifier(entity, Attribute.GENERIC_MOVEMENT_SPEED, "speed_bonus", enemyType.getSpeedBonus() / 100.0);
        EntityUtils.setMaxHealth(entity, enemyType.getMaxHealth());
    }
}
