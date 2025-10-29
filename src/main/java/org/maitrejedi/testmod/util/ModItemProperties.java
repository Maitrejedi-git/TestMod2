package org.maitrejedi.testmod.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import org.maitrejedi.testmod.init.ModItems;
import org.zeith.hammerlib.util.mcf.Resources;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        makeBallLightningStaff();
    }

    private static void makeBallLightningStaff() {
        ItemProperties.register(
                ModItems.BALL_LIGHTNING_STAFF,
                Resources.location("minecraft", "charge"),
                (stack, level, entity, seed) ->
                entity == null || entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 60.0F);
        ItemProperties.register(
                ModItems.BALL_LIGHTNING_STAFF,
                Resources.location("minecraft", "charging"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }
}
