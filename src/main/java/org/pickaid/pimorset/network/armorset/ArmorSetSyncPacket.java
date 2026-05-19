package org.pickaid.pimorset.network.armorset;

import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import org.pickaid.pimorset.api.registry.ArmorSetRegistry;
import org.pickaid.pimorset.armorset.ArmorSet;
import org.pickaid.pimorset.armorset.capability.IArmorSetCapability;
import org.pickaid.pimorset.handlers.client.ArmorSetSyncClientHandler;

/**
 * Sync the armor set for client players.
 * @author Mihono
 */
@SerialClass
public class ArmorSetSyncPacket extends SerialPacketBase {
    @SerialClass.SerialField
    public String activeSetIdentifier;

    public ArmorSetSyncPacket() {}

    public ArmorSetSyncPacket(IArmorSetCapability cap) {
        ArmorSet activeSet = cap.getActiveSet();
        ResourceLocation id = ArmorSetRegistry.getRegistry().getKey(activeSet);
        this.activeSetIdentifier = id != null ? id.toString() : ArmorSetRegistry.EMPTY.toString();
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            if (context.getDirection().getReceptionSide().isClient()) {
                handleClientSide();
            }
        });
        context.setPacketHandled(true);
    }

    private void handleClientSide() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ArmorSetSyncClientHandler.handle(activeSetIdentifier));
    }
}
