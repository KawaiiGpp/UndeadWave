package com.akira.undeadwave.core.enemy;

import com.akira.core.api.Manager;
import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;

public class EnemyManager extends Manager<Enemy<?>> {
    public Enemy<?> fromType(EnemyType type) {
        Validate.notNull(type);

        return CommonUtils.singleMatch(copySet().stream(),
                e -> type.equals(e.getEnemyType()));
    }

    public Enemy<?> fromString(String string) {
        Validate.notNull(string);

        EnemyType parsed = CommonUtils.getEnumSafely(EnemyType.class, string);
        Validate.notNull(parsed, "Enemy type " + string + " not found.");

        return this.fromType(parsed);
    }
}
