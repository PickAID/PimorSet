package org.pickaid.pimorset.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import org.pickaid.pimorset.api.registry.ArmorSetRegistry;
import org.pickaid.piljs.kubejs.PiEventS;
import org.pickaid.pimorset.armorset.ArmorSet;
import org.pickaid.pimorset.kubejs.contents.armorset.CustomArmorSet;

public class PimorSetJSPlugin extends KubeJSPlugin {
    public static RegistryInfo<ArmorSet> ARMOR_SET = RegistryInfo.of(ArmorSetRegistry.ARMOR_SET_REGISTRY_KEY, ArmorSet.class);

    @Override
    public void registerEvents() {
        PiEventS.GROUP.register();
    }

    @Override
    public void init() {
        ARMOR_SET.addType("basic", CustomArmorSet.Builder.class, CustomArmorSet.Builder::new);
    }
}
