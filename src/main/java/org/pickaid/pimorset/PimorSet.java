package org.pickaid.pimorset;

import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.pickaid.pimorset.api.registry.ArmorSetRegistry;
import org.pickaid.pimorset.armorset.SkillKey;
import org.pickaid.pimorset.armorset.capability.ArmorSetCapability;
import org.pickaid.pimorset.armorset.common.ArmorSetAttackListener;
import org.pickaid.pimorset.armorset.integration.CuriosIntegration;
import org.pickaid.pimorset.handlers.server.ArmorSetHandler;
import org.pickaid.pimorset.handlers.server.SetEffectHandler;
import org.pickaid.pimorset.network.PimorSetNetworkHandler;

@Mod(PimorSet.MOD_ID)
public class PimorSet {
    public static final String MOD_ID = "pimorset";
    public static final L2Registrate REGISTRATE = new L2Registrate(MOD_ID);

    public PimorSet() {
        FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
        IEventBus modEventBus = ctx.getModEventBus();
        initializeRegistries(modEventBus);
        initializeArmorSets(modEventBus);
//		ArmorSetRegistryExample.init();
        modEventBus.addListener(this::onCommonSetup);
        SkillKey.init();
    }

    private void initializeRegistries(IEventBus modEventBus) {
        ArmorSetRegistry.register(modEventBus);
    }

    public void onCommonSetup(final FMLCommonSetupEvent event) {
        PimorSetNetworkHandler.init();
    }

    private void initializeArmorSets(IEventBus modEventBus) {
        AttackEventHandler.register(4000, new ArmorSetAttackListener());
        modEventBus.addListener(ArmorSetCapability::register);
        MinecraftForge.EVENT_BUS.register(new ArmorSetHandler());
        MinecraftForge.EVENT_BUS.register(new SetEffectHandler());
        if (ModList.get().isLoaded("curios")) {
            MinecraftForge.EVENT_BUS.register(new CuriosIntegration());
        }
    }
}
