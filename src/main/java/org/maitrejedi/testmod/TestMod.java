package org.maitrejedi.testmod;

import net.minecraftforge.fml.common.Mod;
import org.zeith.hammerlib.core.adapter.LanguageAdapter;

@Mod(TestMod.MOD_ID)
public class TestMod
{
	public static final String MOD_ID = "testmod";

	public TestMod()
	{
		LanguageAdapter.registerMod(MOD_ID);
	}
	
	/*@CreativeTab.RegisterTab
	public static final CreativeTab MOD_TAB = new CreativeTab(id("root"),
			builder -> builder
					.icon(ItemsMI.BALL_LIGHTNING_STAFF::getDefaultInstance)
					.withTabsBefore(HLConstants.HL_TAB.id())
	);*/

	/*public static ResourceLocation id(String path)
	{
		return Resources.location(MOD_ID, path);
	}*/
}