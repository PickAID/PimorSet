package org.pickaid.pimorset.handlers.server;


import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.pickaid.pibrary.api.event.ConfiguredKeyEvent;
import org.pickaid.pimorset.armorset.common.ArmorSetManager;

public class KeyHandler {
    @SubscribeEvent
    public void onKeyPressed(ConfiguredKeyEvent.Pressed event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var set = ArmorSetManager.getActiveArmorSet(player);
            set.getEffect().onSkillPress(player, event.getKeyData());
        }
    }

    @SubscribeEvent
    public void onKeyReleased(ConfiguredKeyEvent.Released event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var set = ArmorSetManager.getActiveArmorSet(player);
            set.getEffect().onSkillRelease(player, event.getKeyData());
        }
    }

    @SubscribeEvent
    public void onKeyCharging(ConfiguredKeyEvent.Charging event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var set = ArmorSetManager.getActiveArmorSet(player);
            set.getEffect().onSkillCharging(player, event.getKeyData());
        }
    }

    @SubscribeEvent
    public void onKeyHeld(ConfiguredKeyEvent.Held event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var set = ArmorSetManager.getActiveArmorSet(player);
            set.getEffect().onSkillHeld(player, event.getKeyData());
        }
    }

    @SubscribeEvent
    public void onKeyFinish(ConfiguredKeyEvent.Finished event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var set = ArmorSetManager.getActiveArmorSet(player);
            set.getEffect().onSkillFinish(player, event.getKeyData());
        }
    }

    @SubscribeEvent
    public void onKeyTimeOut(ConfiguredKeyEvent.TimeOut event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var set = ArmorSetManager.getActiveArmorSet(player);
            set.getEffect().onSkillTimeOut(player, event.getKeyData());
        }
    }

    @SubscribeEvent
    public void onKeyRapidClick(ConfiguredKeyEvent.RapidClick event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var set = ArmorSetManager.getActiveArmorSet(player);
            set.getEffect().onSkillRapidClick(player, event.getKeyData());
        }
    }

    @SubscribeEvent
    public void onKeyRapidClickFinish(ConfiguredKeyEvent.RapidClickFinish event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var set = ArmorSetManager.getActiveArmorSet(player);
            set.getEffect().onSkillRapidClickFinish(player, event.getKeyData());
        }
    }
}
