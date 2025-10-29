package org.maitrejedi.testmod.init;

import net.minecraft.world.item.Items;
import org.zeith.hammerlib.annotations.ProvideRecipes;
import org.zeith.hammerlib.api.IRecipeProvider;
import org.zeith.hammerlib.event.recipe.RegisterRecipesEvent;

@ProvideRecipes
public class ModRecipes implements IRecipeProvider {
    @Override
    public void provideRecipes(RegisterRecipesEvent event) {
        event.shaped()
                .result(ModItems.BALL_LIGHTNING_STAFF)
                .shape(" a ", " aa", "a  ")
                .map('a', Items.OBSIDIAN)
                .register();
    }
}
