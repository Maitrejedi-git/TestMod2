package org.maitrejedi.testmod.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class BallLightningStaffChargeLoopingSound extends AbstractTickableSoundInstance {
    private final Player player;
    private boolean stop = false;

    public BallLightningStaffChargeLoopingSound(SoundEvent sound, Player player) {
        super(sound, SoundSource.PLAYERS, player.level().random);
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 1.0F;
        this.pitch = 1.0F;
    }

    @Override
    public void tick() {
        if (stop || player == null || player.isRemoved() || player.isDeadOrDying()) {
            this.stop();
            return;
        }

        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    public void stopPlaying() {
        this.stop = true;
    }
}
