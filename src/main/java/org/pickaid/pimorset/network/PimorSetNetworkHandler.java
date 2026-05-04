package org.pickaid.pimorset.network;

import dev.xkmc.l2serial.network.BasePacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import org.pickaid.pibrary.Pibrary;
import org.pickaid.pibrary.network.key.KeyStatePacket;
import org.pickaid.pibrary.network.sound.SoundPacket;
import org.pickaid.pimorset.PimorSet;
import org.pickaid.pimorset.armorset.capability.IArmorSetCapability;
import org.pickaid.pimorset.network.armorset.ArmorSetSyncPacket;

public class PimorSetNetworkHandler {
    public static final BasePacketHandler HANDLER = new BasePacketHandler(
            new ResourceLocation(PimorSet.MOD_ID, "main"),
            1,
            handler -> handler.create(ArmorSetSyncPacket.class, NetworkDirection.PLAY_TO_CLIENT)
    );

    public static void init() {
        HANDLER.registerPackets();
    }

    public static void sendArmorSetSync(ServerPlayer player, IArmorSetCapability cap) {
        HANDLER.toClientPlayer(new ArmorSetSyncPacket(cap), player);
    }
}
