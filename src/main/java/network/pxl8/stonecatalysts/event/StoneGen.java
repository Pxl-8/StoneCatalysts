package network.pxl8.stonecatalysts.event;

import net.minecraft.block.Block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import network.pxl8.stonecatalysts.compat.jei.StoneCatalystRecipe;
import network.pxl8.stonecatalysts.config.Configuration;
import network.pxl8.stonecatalysts.lib.LibMeta;

import java.util.ArrayList;
import java.util.List;
//import vazkii.quark.world.module.NewStoneTypesModule;


@Mod.EventBusSubscriber
public class StoneGen {
    @SubscribeEvent
    public static void genStone(BlockEvent.FluidPlaceBlockEvent event) {
        BlockPos pos = event.getLiquidPos();
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        Block oldBlock = event.getOriginalState().getBlock();
        Block newBlock = event.getNewState().getBlock();

        if (oldBlock.equals(Blocks.WATER) && newBlock.equals(Blocks.STONE)) {
            BlockState catalyst = event.getWorld().getBlockState(new BlockPos(x, y - 1, z));
            doStoneReplacements(event, catalyst);
        }

        if (oldBlock.equals(Blocks.LAVA) && newBlock.equals(Blocks.COBBLESTONE)) {
            BlockState catalyst = event.getWorld().getBlockState(new BlockPos(x, y - 1, z));
            doCobblestoneReplacements(event, catalyst);
        }
    }

    private static void replaceBlock(BlockEvent.FluidPlaceBlockEvent event, BlockState catalyst, BlockState replace) {
        if (catalyst.equals(replace)) { event.setNewState(replace); }
    }

    private static void doStoneReplacements(BlockEvent.FluidPlaceBlockEvent event, BlockState catalyst) {
        if (Configuration.REPLACE_STONE.get()) {
            replaceBlock(event, catalyst, Blocks.GRANITE.getDefaultState());
            replaceBlock(event, catalyst, Blocks.DIORITE.getDefaultState());
            replaceBlock(event, catalyst, Blocks.ANDESITE.getDefaultState());
        }

        //if(ModList.get().isLoaded("quark") && Configuration.ENABLE_QUARK_COMPAT.get()) {
        //    replaceBlock(event, catalyst, NewStoneTypesModule.marbleBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.limestoneBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.jasperBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.slateBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.basaltBlock.getDefaultState());
        //}

        if(!StoneGen.customStoneCatalysts.isEmpty()) {
            for(Block block : StoneGen.customStoneCatalysts) {
                replaceBlock(event, catalyst, block.getDefaultState());
            }
        }
    }

    private static void doCobblestoneReplacements(BlockEvent.FluidPlaceBlockEvent event, BlockState catalyst) {
        if (Configuration.REPLACE_COBBLE.get()) {
            replaceBlock(event, catalyst, Blocks.GRANITE.getDefaultState());
            replaceBlock(event, catalyst, Blocks.DIORITE.getDefaultState());
            replaceBlock(event, catalyst, Blocks.ANDESITE.getDefaultState());
        }

        //if(ModList.get().isLoaded("quark") && Configuration.ENABLE_QUARK_COMPAT.get()) {
        //    replaceBlock(event, catalyst, NewStoneTypesModule.marbleBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.limestoneBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.jasperBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.slateBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.basaltBlock.getDefaultState());
        //}

        if(!StoneGen.customCobbleCatalysts.isEmpty()) {
            for(Block block : StoneGen.customCobbleCatalysts) {
                replaceBlock(event, catalyst, block.getDefaultState());
            }
        }
    }

    private static List<Block> customStoneCatalysts = new ArrayList<>();
    private static List<Block> customCobbleCatalysts = new ArrayList<>();

    public static void getCustomCatalysts() {
        if(!Configuration.CUSTOM_STONE_CATALYSTS.get().isEmpty()) {
            for(String catalyst : Configuration.CUSTOM_STONE_CATALYSTS.get()) {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(catalyst));
                if(!block.equals(Blocks.AIR)) {
                    customStoneCatalysts.add(block);
                    if(Configuration.DEBUG_MESSAGES.get()) { LibMeta.LOG.debug("Added custom stone catalyst: " + block); }
                } else {
                    LibMeta.LOG.warn("Could not find catalyst from namespaced id: " + catalyst);
                }
            }
        }
        if(!Configuration.CUSTOM_COBBLE_CATALYSTS.get().isEmpty()) {
            for(String catalyst : Configuration.CUSTOM_COBBLE_CATALYSTS.get()) {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(catalyst));
                if(!block.equals(Blocks.AIR)) {
                    customCobbleCatalysts.add(block);
                    if(Configuration.DEBUG_MESSAGES.get()) { LibMeta.LOG.debug("Added custom cobble catalyst: " + block); }
                } else {
                    LibMeta.LOG.warn("Could not find catalyst from namespaced id: " + catalyst);
                }
            }
        }
    }

    public static void registerJEIRecipes() {
        ItemStack noCatalyst = new ItemStack(Blocks.BARRIER).setDisplayName(new StringTextComponent("No Catalyst"));
        new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "default_stonegen"), noCatalyst, new ItemStack(Blocks.STONE)).registerStone();
        new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "default_cobblegen"), noCatalyst, new ItemStack(Blocks.COBBLESTONE)).registerCobblestone();

        if (Configuration.REPLACE_STONE.get()) {
            new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "granite_stonegen"), new ItemStack(Blocks.GRANITE)).registerStone();
            new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "diorite_stonegen"), new ItemStack(Blocks.DIORITE)).registerStone();
            new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "andesite_stonegen"), new ItemStack(Blocks.ANDESITE)).registerStone();
        }

        if (Configuration.REPLACE_COBBLE.get()) {
            new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "granite_cobblegen"), new ItemStack(Blocks.GRANITE)).registerCobblestone();
            new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "diorite_cobblegen"), new ItemStack(Blocks.DIORITE)).registerCobblestone();
            new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "andesite_cobblegen"), new ItemStack(Blocks.ANDESITE)).registerCobblestone();
        }

        if(!StoneGen.customStoneCatalysts.isEmpty()) {
            for(Block block : StoneGen.customStoneCatalysts) {
                String resource_path = block.getRegistryName().getNamespace() + "_" + block.getRegistryName().getPath() + "_stonegen";
                new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, resource_path), new ItemStack(block)).registerStone();
            }
        }

        if(!StoneGen.customCobbleCatalysts.isEmpty()) {
            for(Block block : StoneGen.customCobbleCatalysts) {
                String resource_path = block.getRegistryName().getNamespace() + "_" + block.getRegistryName().getPath() + "_cobblegen";
                new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, resource_path), new ItemStack(block)).registerCobblestone();
            }
        }
    }
}
