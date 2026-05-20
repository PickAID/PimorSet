package org.pickaid.pimorset.runtime.content;

import net.minecraftforge.eventbus.api.IEventBus;
import org.pickaid.picontent.api.block.PiBlockBuilder;
import org.pickaid.picontent.api.block.PiBlockLoot;
import org.pickaid.picontent.api.block.PiBlockModels;
import org.pickaid.picontent.api.block.PiBlockPlacement;
import org.pickaid.picontent.api.block.PiBlockPresets;
import org.pickaid.picontent.api.block.PiBlockShapes;
import org.pickaid.picontent.api.blockentity.PiBlockEntityBuilder;
import org.pickaid.picontent.api.blockentity.PiContentRenderers;
import org.pickaid.picontent.api.blockentity.PiContentSync;
import org.pickaid.picontent.api.forge.PiContentRegistrationPlan;
import org.pickaid.picontent.api.forge.PiForgeContentEntries;
import org.pickaid.picontent.api.forge.PiForgeContentRegistry;
import org.pickaid.picontent.api.item.PiItemBuilder;
import org.pickaid.picontent.api.item.PiItemModels;
import org.pickaid.picontent.api.item.PiItemPresets;
import org.pickaid.pimorset.PimorSet;

public final class PimorSetP0Content {
    private static final PiContentRegistrationPlan REGISTRATION_PLAN = PiContentRegistrationPlan.create(PimorSet.MOD_ID)
            .item(PiItemBuilder.named("storm_staff")
                    .section("pimorset")
                    .properties(PiItemPresets.staff())
                    .itemTag("pimorset:staves")
                    .lang("Storm Staff")
                    .model(PiItemModels.handheld("item/storm_staff"))
                    .plan())
            .blockEntityBlock(PiBlockBuilder.named("storm_altar")
                    .section("pimorset")
                    .properties(PiBlockPresets.stoneMachine().lightLevel(4).noOcclusion())
                    .blockTag("pimorset:altars")
                    .itemTag("pimorset:altar_items")
                    .shape(PiBlockShapes.box16(1, 0, 1, 15, 12, 15))
                    .placement(PiBlockPlacement.horizontal())
                    .loot(PiBlockLoot.self())
                    .blockstate(PiBlockModels.horizontal("block/storm_altar"))
                    .simpleItem()
                    .creative("pimorset")
                    .plan())
            .blockEntity(PiBlockEntityBuilder.named("storm_altar")
                    .validBlock("pimorset:storm_altar")
                    .dirtySync(PiContentSync.tracking("storm_altar_state"))
                    .renderer(PiContentRenderers.blockEntity("storm_altar"))
                    .plan());

    private static final PiForgeContentRegistry REGISTRY = PiForgeContentRegistry.create(REGISTRATION_PLAN)
            .registerItem("storm_staff")
            .registerBlockEntity("storm_altar", PimorSetStormAltarBlockEntity::new)
            .registerBlock("storm_altar");

    private PimorSetP0Content() {
    }

    public static void register(IEventBus modEventBus) {
        REGISTRY.registerAllContent(modEventBus);
    }

    public static PiContentRegistrationPlan registrationPlan() {
        return REGISTRATION_PLAN;
    }

    public static PiForgeContentEntries entries() {
        return REGISTRY.entries();
    }
}
