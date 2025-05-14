package org.pickaid.pimorset.armorset;

import net.minecraftforge.client.settings.KeyModifier;
import org.pickaid.pibrary.Pibrary;
import org.pickaid.pibrary.content.key.registry.KeyConfig;
import org.pickaid.pibrary.content.key.registry.KeyRegistry;
import org.lwjgl.glfw.GLFW;

public class SkillKey {
    public static KeyConfig SKILL_KEY;
    public static void init() {
        SKILL_KEY = KeyRegistry.register(new KeyConfig.Builder(Pibrary.MOD_ID, "armor_set_skill", GLFW.GLFW_KEY_G)
                .category("armor_set")
                .enableCharging()
                .maxPower(4.0f)
                .autoReleaseOnMax(true)
                .setChargingTimeTolerance(500)
                .setTimeoutTime(100000)
                .enableRapidClick()
                .setMaxRapidClickCount(10)
                .showDebugMessage()
//                        .setKeyModifier(KeyModifier.ALT)
                .build()
        );
    }
}