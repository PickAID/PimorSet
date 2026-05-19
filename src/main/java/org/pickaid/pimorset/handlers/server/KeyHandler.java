package org.pickaid.pimorset.handlers.server;


import net.minecraft.server.level.ServerPlayer;
import org.pickaid.pikey.api.PiInputIntent;
import org.pickaid.pikey.api.PiInputPhase;
import org.pickaid.pimorset.armorset.common.ArmorSetManager;

public class KeyHandler {
    public void dispatch(ServerPlayer player, PiInputIntent intent) {
        var set = ArmorSetManager.getActiveArmorSet(player);
        var effect = set.getEffect();
        PiInputPhase phase = intent.phase();
        if (phase == PiInputPhase.PRESSED || phase == PiInputPhase.STARTED) {
            effect.onSkillPress(player, intent);
        } else if (phase == PiInputPhase.HELD) {
            effect.onSkillHeld(player, intent);
            effect.onSkillCharging(player, intent);
        } else if (phase == PiInputPhase.RELEASED) {
            effect.onSkillRelease(player, intent);
        } else if (phase == PiInputPhase.PERFORMED) {
            effect.onSkillFinish(player, intent);
        } else if (phase == PiInputPhase.CANCELED) {
            effect.onSkillTimeOut(player, intent);
        }
    }
}
