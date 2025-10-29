package org.maitrejedi.testmod.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;
import org.maitrejedi.testmod.init.ModEntities;
import org.maitrejedi.testmod.init.ModItems;
import org.maitrejedi.testmod.sound.BallLightningLoopSound;

import java.util.List;

public class BallLightningProjectileEntity extends Projectile implements ItemSupplier {
    private static final EntityDataAccessor<Float> SIZE =
            SynchedEntityData.defineId(BallLightningProjectileEntity.class, EntityDataSerializers.FLOAT);
    private int chargeLevel;

    public BallLightningProjectileEntity(EntityType<? extends BallLightningProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BallLightningProjectileEntity(Level pLevel) {
        this(ModEntities.BALL_LIGHTNING_PROJECTILE_ENTITY, pLevel);
    }

    public BallLightningProjectileEntity(Level pLevel, int chargeLevel) {
        this(pLevel);
        this.chargeLevel = chargeLevel;
    }

    @Override
    public void tick() {
        super.tick();

        this.move(MoverType.SELF, this.getDeltaMovement());

        if (!this.level().isClientSide) {
            HitResult hit = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hit.getType() != HitResult.Type.MISS) {
                this.onHit(hit);
            }
        }

        double radius = 1.5 * chargeLevel;
        float damage = 2.0f * (1 + chargeLevel);

        AABB area = new AABB(
                this.getX() - radius, this.getY() - radius, this.getZ() - radius,
                this.getX() + radius, this.getY() + radius, this.getZ() + radius
        );

        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, area, e -> e.isAlive() && e != this.getOwner());

        for (LivingEntity target : targets) {
            target.hurt(this.damageSources().indirectMagic(this, this.getOwner()), damage);
        }

        if (this.tickCount > 200) {
            discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.level().isClientSide) {
            summonLightningStrike(result.getEntity().blockPosition(), (ServerLevel) this.level());

            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (!this.level().isClientSide) {
            summonLightningStrike(result.getBlockPos(), (ServerLevel) this.level());

            this.discard();
        }
    }

    private void summonLightningStrike(BlockPos hitPos, ServerLevel serverLevel) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverLevel);
        lightning.moveTo(Vec3.atBottomCenterOf(hitPos));
        serverLevel.addFreshEntity(lightning);

        if (chargeLevel == 2) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx == 0 && dz == 0) {
                        continue;
                    }

                    LightningBolt extra = EntityType.LIGHTNING_BOLT.create(serverLevel);
                    extra.moveTo(hitPos.getX() + dx, hitPos.getY(), hitPos.getZ() + dz);
                    serverLevel.addFreshEntity(extra);
                }
            }
        }
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (level().isClientSide) {
            Minecraft.getInstance().getSoundManager().play(new BallLightningLoopSound(this, 0.6f, 32f));
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return true;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SIZE, 1.0F);
    }

    /*@Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }*/

    public void setSize(float size) {
        this.entityData.set(SIZE, size);
    }

    public float getSize() {
        return this.entityData.get(SIZE);
    }

    @Override
    public ItemStack getItem() {
        return ModItems.BALL_LIGHTNING.getDefaultInstance();
    }
}
