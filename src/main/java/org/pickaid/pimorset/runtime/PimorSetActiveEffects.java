package org.pickaid.pimorset.runtime;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import net.minecraft.world.entity.LivingEntity;
import org.pickaid.pimorset.armorset.ISetEffect;

public final class PimorSetActiveEffects {
    private static final Map<LivingEntity, ISetEffect> ACTIVE_EFFECTS = new WeakHashMap<>();

    private PimorSetActiveEffects() {
    }

    public static void put(LivingEntity entity, ISetEffect effect) {
        ACTIVE_EFFECTS.put(entity, effect);
    }

    public static void remove(LivingEntity entity) {
        ACTIVE_EFFECTS.remove(entity);
    }

    public static Optional<ISetEffect> get(LivingEntity entity) {
        return Optional.ofNullable(ACTIVE_EFFECTS.get(entity));
    }
}
