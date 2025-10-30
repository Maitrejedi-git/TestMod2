package org.maitrejedi.testmod.init;

import net.minecraft.world.level.block.entity.BlockEntityType;
import org.maitrejedi.testmod.block.entity.LightningGeneratorBlockEntity;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;
import org.zeith.hammerlib.api.forge.BlockAPI;

@SimplyRegister
public interface ModBlockEntities {
    @RegistryName("lightning_generator")
    BlockEntityType<LightningGeneratorBlockEntity> LIGHTNING_GENERATOR_BLOCK_ENTITY =
            BlockAPI.createBlockEntityType(LightningGeneratorBlockEntity::new, ModBlocks.LIGHTNING_GENERATOR_BLOCK);
}
