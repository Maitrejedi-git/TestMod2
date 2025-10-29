package org.maitrejedi.testmod.item;


import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.maitrejedi.testmod.entity.BallLightningProjectileEntity;
import org.maitrejedi.testmod.init.ModSounds;
import org.maitrejedi.testmod.sound.BallLightningStaffChargeLoopingSound;

public class BallLightningStaff extends Item {
    private static final int CHARGE_SOUND_LENGTH_TICKS = 80;

    private BallLightningStaffChargeLoopingSound chargeSound;
    private int chargeSoundTicks = 0;
    private float charge = 0;

    public BallLightningStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (pLevel.isClientSide) {
            startChargeSound(pPlayer);
        }

        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide || !(entity instanceof Player player)) {
            return;
        }

        if (++chargeSoundTicks >= CHARGE_SOUND_LENGTH_TICKS) {
            startChargeSound(player);
            chargeSoundTicks = 0;
        }

        updateCharge(stack, entity, remainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!pLevel.isClientSide && pLivingEntity instanceof Player player) {
            stopChargeSound();

            if (charge < 0.4F) {
                return;
            }

            double px = player.getX();
            double py = player.getEyeY() - 0.1;
            double pz = player.getZ();

            int chargeLevel = 1;
            double speed = 0.3;
            float size = 2.0F;
            if (charge >= 0.9F) {
                chargeLevel = 2;
                speed = 0.2;
                size = 4.0F;
            }

            BallLightningProjectileEntity ballLightning = new BallLightningProjectileEntity(pLevel, chargeLevel);
            ballLightning.setSize(size);

            ballLightning.setOwner(player);
            ballLightning.setPos(px, py, pz);

            Vec3 look = player.getLookAngle();
            ballLightning.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);

            pLevel.addFreshEntity(ballLightning);

            pStack.hurtAndBreak(1, player, (p) -> {
                p.broadcastBreakEvent(pStack.getEquipmentSlot());
            });
        }
    }

    private void updateCharge(ItemStack stack, LivingEntity entity, int remainingUseDuration) {
        charge = (stack.getUseDuration() - remainingUseDuration) / 60.0F;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }

    private void startChargeSound(Player player) {
        stopChargeSound();
        Minecraft mc = Minecraft.getInstance();
        chargeSound = new BallLightningStaffChargeLoopingSound(ModSounds.BALL_LIGHTNING_STAFF_CHARGE, player);
        mc.getSoundManager().play(chargeSound);
    }

    private void stopChargeSound() {
        if (chargeSound != null) {
            chargeSound.stopPlaying();
            chargeSound = null;
        }
    }
}
