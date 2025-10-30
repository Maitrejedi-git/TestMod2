package org.maitrejedi.testmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.maitrejedi.testmod.block.LightningGeneratorBlock;
import org.zeith.hammerlib.tiles.TileSyncableTickable;

import javax.annotation.Nullable;
import java.util.List;

public class LightningGeneratorBlockEntity extends TileSyncableTickable {
    private static final int MAX_ENERGY_CAPACITY = 160000;
    private static final int MAX_ENERGY = 16000;
    private static final int MAX_TRANSFER = 16000;
    private static final double MAX_RADIUS = 8.0;
    private static final int MAX_POWERED_TIME = 60;

    private int poweredTime = 0;

    private final EnergyStorage energy = new EnergyStorage(MAX_ENERGY_CAPACITY, MAX_ENERGY, MAX_ENERGY);
    private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);

    public LightningGeneratorBlockEntity(BlockEntityType<LightningGeneratorBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, BlockEntity be) {
        if (level.isClientSide) {
            return;
        }

        if (poweredTime > 0 && --poweredTime % 20 == 0) {
            level.setBlock(pos, state.setValue(LightningGeneratorBlock.POWER, state.getValue(LightningGeneratorBlock.POWER) - 1), 3);
        }

        List<LightningBolt> bolts = level.getEntitiesOfClass(LightningBolt.class,
                new AABB(pos).inflate(MAX_RADIUS));

        for (LightningBolt bolt : bolts) {
            if (bolt.getTags().contains("used_by_generator")) {
                continue;
            }

            double dist = bolt.position().distanceTo(Vec3.atCenterOf(pos));
            if (dist <= MAX_RADIUS) {
                double factor = 1.0 - (Math.max(0, dist - 1) / MAX_RADIUS);
                int generated = (int) (MAX_ENERGY * factor);
                energy.receiveEnergy(generated, false);

                bolt.addTag("used_by_generator");

                poweredTime = MAX_POWERED_TIME;
                level.setBlock(pos, state.setValue(LightningGeneratorBlock.POWER, 3), 3);

                break;
            }
        }

        outputEnergy(pos);
    }

    private void outputEnergy(BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockEntity neighbor = level.getBlockEntity(pos.relative(dir));
            if (neighbor == null) {
                continue;
            }

            neighbor.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite()).ifPresent(handler -> {
                int extracted = energy.extractEnergy(MAX_TRANSFER, false);
                handler.receiveEnergy(extracted, false);
            });
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY)
            return energyCap.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCap.invalidate();
    }
}
