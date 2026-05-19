package org.pickaid.pimorset.armorset;

public record ItemHurtEffectResult(boolean canceled, int replacementDamage) {
    public static ItemHurtEffectResult cancel() {
        return new ItemHurtEffectResult(true, 0);
    }

    public static ItemHurtEffectResult unmodified() {
        return new ItemHurtEffectResult(false, -1);
    }
}
