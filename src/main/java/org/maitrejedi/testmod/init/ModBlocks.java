package org.maitrejedi.testmod.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.maitrejedi.testmod.block.LightningGeneratorBlock;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;

@SimplyRegister
public interface ModBlocks {
    @RegistryName("lightning_generator")
    LightningGeneratorBlock LIGHTNING_GENERATOR_BLOCK = new LightningGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
}