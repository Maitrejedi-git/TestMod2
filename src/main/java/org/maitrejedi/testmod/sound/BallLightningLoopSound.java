package org.maitrejedi.testmod.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.maitrejedi.testmod.entity.BallLightningProjectileEntity;
import org.maitrejedi.testmod.init.ModSounds;

@OnlyIn(Dist.CLIENT)
public class BallLightningLoopSound extends AbstractTickableSoundInstance {
    private final BallLightningProjectileEntity entity;
    private final float maxDistance;
    private final float baseVolume;

    public BallLightningLoopSound(BallLightningProjectileEntity entity, float baseVolume, float maxDistance) {
        super(ModSounds.BALL_LIGHTNING, SoundSource.AMBIENT, RandomSource.create());
        this.entity = entity;
        this.looping = true;
        this.volume = 0.6F;
        this.pitch = 1.0F;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.attenuation = Attenuation.LINEAR;
        this.baseVolume = baseVolume;
        this.maxDistance = maxDistance;
    }

    @Override
    public void tick() {
        if (this.entity.isRemoved() || !this.entity.isAlive()) {
            this.stop();
            return;
        }

        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();

        if (Minecraft.getInstance().player != null) {
            double dx = Minecraft.getInstance().player.getX() - entity.getX();
            double dy = Minecraft.getInstance().player.getY() - entity.getY();
            double dz = Minecraft.getInstance().player.getZ() - entity.getZ();
            double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);

            float newVolume;
            if (dist < maxDistance) {
                newVolume = baseVolume * (1.0f - (float) (dist / maxDistance));
            } else {
                newVolume = 0f;
            }

            if (newVolume < 0f) newVolume = 0f;
            if (newVolume > 1.0f) newVolume = 1.0f;

            this.volume = newVolume;
        }
    }
}
