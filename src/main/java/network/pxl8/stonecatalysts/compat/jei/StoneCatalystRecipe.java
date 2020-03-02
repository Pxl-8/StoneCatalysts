package network.pxl8.stonecatalysts.compat.jei;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StoneCatalystRecipe {
    public final ResourceLocation name;
    public final ItemStack catalyst_in;
    public final ItemStack catalyst_out;

    public StoneCatalystRecipe(ResourceLocation name, ItemStack catalyst) {
        this(name, catalyst, catalyst);
    }

    public StoneCatalystRecipe(ResourceLocation name, ItemStack catalyst_in, ItemStack catalyst_out) {
        this.name = name;
        this.catalyst_in = catalyst_in;
        this.catalyst_out = catalyst_out;
    }

    public StoneCatalystRecipe registerStone() {
        StoneCatalystsJEIPlugin.STONE_CATALYST_RECIPES.put(this.name, this);
        return this;
    }

    public StoneCatalystRecipe registerCobblestone() {
        StoneCatalystsJEIPlugin.COBBLESTONE_CATALYST_RECIPES.put(this.name, this);
        return this;
    }
}
