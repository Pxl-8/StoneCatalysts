package network.pxl8.stonecatalysts.compat.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import network.pxl8.stonecatalysts.lib.LibMeta;

import java.util.HashMap;
import java.util.Map;

@JEIPlugin
public class StoneCatalystsJEIPlugin implements IModPlugin {
    public static final ResourceLocation STONE_CATALYSTS        = new ResourceLocation(LibMeta.MOD_ID, "category_stone_gen");
    public static final ResourceLocation COBBLESTONE_CATALYSTS  = new ResourceLocation(LibMeta.MOD_ID, "category_cobblestone_gen");
    public static final Map<ResourceLocation, StoneCatalystRecipe> STONE_CATALYST_RECIPES = new HashMap<>();
    public static final Map<ResourceLocation, StoneCatalystRecipe> COBBLESTONE_CATALYST_RECIPES = new HashMap<>();

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(STONE_CATALYST_RECIPES.values(), STONE_CATALYSTS.toString());
        registry.addRecipes(COBBLESTONE_CATALYST_RECIPES.values(), COBBLESTONE_CATALYSTS.toString());

        registry.addIngredientInfo(new ItemStack(Items.LAVA_BUCKET), VanillaTypes.ITEM, I18n.format("jei.stonecatalysts:information_tab_desc"));

        registry.addRecipeCatalyst(new ItemStack(Items.LAVA_BUCKET), STONE_CATALYSTS.toString());
        registry.addRecipeCatalyst(new ItemStack(Items.WATER_BUCKET), STONE_CATALYSTS.toString());
        registry.addRecipeCatalyst(new ItemStack(Items.LAVA_BUCKET), COBBLESTONE_CATALYSTS.toString());
        registry.addRecipeCatalyst(new ItemStack(Items.WATER_BUCKET), COBBLESTONE_CATALYSTS.toString());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(
                new StoneCatalystCategory(guiHelper),
                new CobblestoneCatalystCategory(guiHelper)
        );
    }
}
