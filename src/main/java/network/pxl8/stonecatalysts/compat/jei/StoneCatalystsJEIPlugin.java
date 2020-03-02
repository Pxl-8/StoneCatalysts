package network.pxl8.stonecatalysts.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import network.pxl8.stonecatalysts.lib.LibMeta;

import java.util.HashMap;
import java.util.Map;

@JeiPlugin
public class StoneCatalystsJEIPlugin implements IModPlugin {
    public static final ResourceLocation STONE_CATALYSTS        = new ResourceLocation(LibMeta.MOD_ID, "category_stone_gen");
    public static final ResourceLocation COBBLESTONE_CATALYSTS  = new ResourceLocation(LibMeta.MOD_ID, "category_cobblestone_gen");
    public static final Map<ResourceLocation, StoneCatalystRecipe> STONE_CATALYST_RECIPES = new HashMap<>();
    public static final Map<ResourceLocation, StoneCatalystRecipe> COBBLESTONE_CATALYST_RECIPES = new HashMap<>();

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(LibMeta.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new StoneCatalystCategory(guiHelper),
                new CobblestoneCatalystCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(STONE_CATALYST_RECIPES.values(), STONE_CATALYSTS);
        registration.addRecipes(COBBLESTONE_CATALYST_RECIPES.values(), COBBLESTONE_CATALYSTS);

        registration.addIngredientInfo(new ItemStack(Items.LAVA_BUCKET), VanillaTypes.ITEM, I18n.format("jei.stonecatalysts:information_tab_desc"));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Items.LAVA_BUCKET), STONE_CATALYSTS);
        registration.addRecipeCatalyst(new ItemStack(Items.WATER_BUCKET), STONE_CATALYSTS);
        registration.addRecipeCatalyst(new ItemStack(Items.LAVA_BUCKET), COBBLESTONE_CATALYSTS);
        registration.addRecipeCatalyst(new ItemStack(Items.WATER_BUCKET), COBBLESTONE_CATALYSTS);
    }
}
