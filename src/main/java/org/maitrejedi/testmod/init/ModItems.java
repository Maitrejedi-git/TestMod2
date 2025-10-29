package org.maitrejedi.testmod.init;

import net.minecraft.world.item.Item;
import org.maitrejedi.testmod.item.BallLightningStaff;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;

@SimplyRegister
public interface ModItems {
	@RegistryName("ball_lightning_staff")
	BallLightningStaff BALL_LIGHTNING_STAFF = new BallLightningStaff(new Item.Properties().durability(100));
	@RegistryName("ball_lightning")
	Item BALL_LIGHTNING = new Item(new Item.Properties());
}