package network.pxl8.stonecatalysts.compat.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import network.pxl8.stonecatalysts.lib.LibMeta;

import java.util.Collections;
import java.util.List;

public class CobblestoneCatalystCategory implements IRecipeCategory<StoneCatalystRecipe> {
    private final IDrawable background;

    public CobblestoneCatalystCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(new ResourceLocation(LibMeta.MOD_ID, "textures/gui/jei/cobblestone_gen.png"), 0, 0, 117, 74);
    }

    @Override
    public ResourceLocation getUid() {
        return StoneCatalystsJEIPlugin.COBBLESTONE_CATALYSTS;
    }

    @Override
    public Class<? extends StoneCatalystRecipe> getRecipeClass() {
        return StoneCatalystRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.format("jei." + StoneCatalystsJEIPlugin.COBBLESTONE_CATALYSTS);
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public List<String> getTooltipStrings(StoneCatalystRecipe recipe, double mouseX, double mouseY) {
        String output_tooltip = I18n.format("jei.stonecatalysts:tooltip_cobblestone_catalyst");
        String arrow_tooltip = I18n.format("jei.stonecatalysts:tooltip_flowing_lava");
        if (mouseX > 58 && mouseX < 81 && mouseY > 36 && mouseY < 52) {
            return Lists.newArrayList(output_tooltip.split(","));
        }
        if (mouseX > 18 && mouseX < 35 && mouseY > 36 && mouseY < 53) {
            return Lists.newArrayList(arrow_tooltip.split(","));
        }
        return Collections.emptyList();
    }

    @Override
    public void setIngredients(StoneCatalystRecipe catalystRecipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, catalystRecipe.catalyst_in);
        ingredients.setOutput(VanillaTypes.ITEM, catalystRecipe.catalyst_out);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, StoneCatalystRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup group = layout.getItemStacks();
        IGuiFluidStackGroup fluid_group = layout.getFluidStacks();
        //Catalyst input and output
        group.init(0, false, 18, 55);
        group.set(0, recipe.catalyst_in);
        group.init(1, false, 94, 36);
        group.set(1, recipe.catalyst_out);
        //Lava and Water
        fluid_group.init(0, false, 1, 37);
        fluid_group.set(0, new FluidStack(Fluids.LAVA, 1000));
        fluid_group.init(1, false, 37,37);
        fluid_group.set(1, new FluidStack(Fluids.WATER, 1000));
    }
}