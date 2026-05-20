package org.pickaid.pimorset.runtime;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.junit.jupiter.api.Test;
import org.pickaid.picontent.api.forge.PiContentRegistrationPlan;
import org.pickaid.picontent.api.forge.PiForgeContentEntries;
import org.pickaid.picontent.api.forge.PiForgeRegistryEntry;
import org.pickaid.pimorset.runtime.content.PimorSetP0Content;
import org.pickaid.pimorset.runtime.content.PimorSetStormAltarBlockEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

final class PimorSetP0ContentRegistrationTest {
    @Test
    void p0ContentRegistersStormStaffAltarAndBlockEntityThroughPiContent() {
        PiContentRegistrationPlan plan = PimorSetP0Content.registrationPlan();
        PiForgeContentEntries entries = PimorSetP0Content.entries();
        PiForgeRegistryEntry<Item> staff = entries.item("storm_staff");
        PiForgeRegistryEntry<Block> altar = entries.block("storm_altar");
        PiForgeRegistryEntry<BlockEntityType<PimorSetStormAltarBlockEntity>> altarBlockEntity = entries.blockEntity("storm_altar");

        assertEquals("pimorset:storm_staff", plan.item("storm_staff").id());
        assertEquals("pimorset:storm_altar", plan.block("storm_altar").id());
        assertEquals("pimorset:storm_altar", plan.blockEntity("storm_altar").id());
        assertEquals("pimorset:storm_staff", staff.id());
        assertEquals("pimorset:storm_altar", altar.id());
        assertEquals("pimorset:storm_altar", altarBlockEntity.id());
        assertFalse(entries.itemEntries().isEmpty());
        assertFalse(entries.blockEntries().isEmpty());
        assertFalse(entries.blockEntityEntries().isEmpty());
    }
}
