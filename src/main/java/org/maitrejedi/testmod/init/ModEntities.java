package org.maitrejedi.testmod.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.maitrejedi.testmod.entity.BallLightningProjectileEntity;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;

@SimplyRegister
public interface ModEntities {
    @RegistryName("ball_lightning")
    EntityType<BallLightningProjectileEntity> BALL_LIGHTNING_PROJECTILE_ENTITY =
            EntityType.Builder.<BallLightningProjectileEntity>of(BallLightningProjectileEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("ball_lightning");
}
