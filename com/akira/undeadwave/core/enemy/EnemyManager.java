package com.akira.undeadwave.core.enemy;

import com.akira.core.api.Manager;
import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

public class EnemyManager extends Manager<Enemy<?>> {
    public Enemy<?> fromType(EnemyType type) {
        Validate.notNull(type);

        return CommonUtils.singleMatch(copySet().stream(),
                e -> type == e.getEnemyType());
    }

    public Enemy<?> fromString(String string) {
        Validate.notNull(string);

        EnemyType parsed = CommonUtils.getEnumSafely(EnemyType.class, string);
        Validate.notNull(parsed, "Enemy type " + string + " not found.");

        return this.fromType(parsed);
    }

    public Enemy<?> fromMonster(Monster monster) {
        Validate.notNull(monster);

        return CommonUtils.singleMatch(copySet().stream(),
                e -> e.matches(monster), false);
    }

    public Enemy<?> fromEntity(Entity entity) {
        Validate.notNull(entity);

        if (!(entity instanceof Monster monster)) return null;
        return this.fromMonster(monster);
    }
}
