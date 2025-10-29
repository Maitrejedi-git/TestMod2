package org.maitrejedi.testmod.event;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.maitrejedi.testmod.entity.client.BallLightningProjectileRenderer;
import org.maitrejedi.testmod.init.ModEntities;
import org.maitrejedi.testmod.util.ModItemProperties;

import static org.maitrejedi.testmod.TestMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModItemProperties.addCustomItemProperties();

        EntityRenderers.register(ModEntities.BALL_LIGHTNING_PROJECTILE_ENTITY, BallLightningProjectileRenderer::new);
    }
}
