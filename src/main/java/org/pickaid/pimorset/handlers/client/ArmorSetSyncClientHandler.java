package org.pickaid.pimorset.handlers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.pickaid.pimorset.api.registry.ArmorSetRegistry;
import org.pickaid.pimorset.armorset.ArmorSet;
import org.pickaid.pimorset.armorset.capability.ArmorSetCapability;

public final class ArmorSetSyncClientHandler {
    private ArmorSetSyncClientHandler() {
    }

    public static void handle(String activeSetIdentifier) {
        Player clientPlayer = Minecraft.getInstance().player;
        if (clientPlayer != null) {
            clientPlayer.getCapability(ArmorSetCapability.ARMOR_SET_CAPABILITY).ifPresent(cap -> {
                ResourceLocation id = ResourceLocation.tryParse(activeSetIdentifier);
                ArmorSet activeSet = id == null ? null : ArmorSetRegistry.getRegistry().getValue(id);
                cap.setActiveSet(activeSet != null ? activeSet : ArmorSetRegistry.EMPTY_SET.get());
            });
        }
    }
}
