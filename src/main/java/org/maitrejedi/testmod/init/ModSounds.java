package org.maitrejedi.testmod.init;

import net.minecraft.sounds.SoundEvent;
import org.maitrejedi.testmod.TestMod;
import org.zeith.hammerlib.annotations.RegistryName;
import org.zeith.hammerlib.annotations.SimplyRegister;
import org.zeith.hammerlib.util.mcf.Resources;

@SimplyRegister
public interface ModSounds {
    @RegistryName("ball_lightning_staff_charge")
    SoundEvent BALL_LIGHTNING_STAFF_CHARGE =
            SoundEvent.createVariableRangeEvent(Resources.location(TestMod.MOD_ID, "ball_lightning_staff_charge"));
    @RegistryName("ball_lightning")
    SoundEvent BALL_LIGHTNING =
            SoundEvent.createVariableRangeEvent(Resources.location(TestMod.MOD_ID, "ball_lightning"));
}
