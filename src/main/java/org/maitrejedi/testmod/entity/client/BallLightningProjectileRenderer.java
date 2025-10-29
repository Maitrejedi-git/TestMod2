package org.maitrejedi.testmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import org.maitrejedi.testmod.entity.BallLightningProjectileEntity;

public class BallLightningProjectileRenderer extends ThrownItemRenderer<BallLightningProjectileEntity> {
    public BallLightningProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(BallLightningProjectileEntity pEntity, float pEntityYaw, float pPartialTicks,
                       PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        float scale = pEntity.getSize();
        pPoseStack.scale(scale, scale, scale);

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

        pPoseStack.popPose();
    }
}
