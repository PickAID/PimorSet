package org.pickaid.pimorset.runtime.content;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.pickaid.picontent.api.forge.PiForgeRegisteredBlockEntity;

public final class PimorSetStormAltarBlockEntity extends BlockEntity {
    public PimorSetStormAltarBlockEntity(
            PiForgeRegisteredBlockEntity<PimorSetStormAltarBlockEntity> registered,
            BlockPos pos,
            BlockState state
    ) {
        super(registered.type().get(), pos, state);
    }
}
