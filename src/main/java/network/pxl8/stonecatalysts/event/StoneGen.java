package network.pxl8.stonecatalysts.event;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import network.pxl8.stonecatalysts.compat.jei.StoneCatalystRecipe;
import network.pxl8.stonecatalysts.config.Conf;
import network.pxl8.stonecatalysts.lib.LibMeta;
import network.pxl8.stonecatalysts.lib.LibTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber
public class StoneGen {
    @SubscribeEvent
    public static void genStone(BlockEvent.FluidPlaceBlockEvent event) {
        BlockPos pos = event.getLiquidPos();
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        Block oldBlock = event.getOriginalState().getBlock();
        Block newBlock = event.getNewState().getBlock();

        if (oldBlock.equals(Blocks.WATER) && newBlock.equals(Blocks.STONE)) {
            IBlockState catalyst = event.getWorld().getBlockState(new BlockPos(x, y - 2, z));
            doStoneReplacements(event, catalyst);
        }

        if (oldBlock.equals(Blocks.FLOWING_LAVA) && newBlock.equals(Blocks.COBBLESTONE)) {
            IBlockState catalyst = event.getWorld().getBlockState(new BlockPos(x, y - 1, z));
            doCobbleReplacements(event, catalyst);
        }
    }

    private static void replaceBlock(BlockEvent.FluidPlaceBlockEvent event, IBlockState catalyst, IBlockState replace) {
        if (catalyst.equals(replace)) { event.setNewState(replace); }
    }

    private static void doStoneReplacements(BlockEvent.FluidPlaceBlockEvent event, IBlockState catalyst) {
        if(!StoneGen.customStoneCatalysts.isEmpty()) {
            for(IBlockState block : StoneGen.customStoneCatalysts) {
                replaceBlock(event, catalyst, block);
            }
        }
    }

    private static void doCobbleReplacements(BlockEvent.FluidPlaceBlockEvent event, IBlockState catalyst) {
        if(!StoneGen.customCobbleCatalysts.isEmpty()) {
            for(IBlockState block : StoneGen.customCobbleCatalysts) {
                replaceBlock(event, catalyst, block);
            }
        }
    }

    private static List<IBlockState> customStoneCatalysts = new ArrayList<>();
    private static List<IBlockState> customCobbleCatalysts = new ArrayList<>();

    public static void getCustomCatalysts() {
        if(!Arrays.asList(Conf.custom_config.CUSTOM_STONE_CATALYSTS).isEmpty()) {
            for(String catalyst : Arrays.asList(Conf.custom_config.CUSTOM_STONE_CATALYSTS)) {
                if (LibTools.getStateFromString(catalyst) == null) {
                    LibMeta.LOG.warn("Failed to add catalyst from: " + catalyst);
                } else {
                    IBlockState block = LibTools.getStateFromString(catalyst);
                    if (!block.getBlock().equals(Blocks.AIR)) {
                        customStoneCatalysts.add(block);
                        if(Conf.custom_config.DEBUG_MESSAGES) { LibMeta.LOG.info("Added custom stone catalyst: " + block.toString()); }
                    } else {
                        LibMeta.LOG.warn("Unable to get block from string. Check if namespace and path are spelled correctly or meta is right");
                        LibMeta.LOG.warn("Failed to add catalyst from: " + catalyst);
                    }
                }
            }
        }
        if(!Arrays.asList(Conf.custom_config.CUSTOM_COBBLE_CATALYSTS).isEmpty()) {
            for(String catalyst : Arrays.asList(Conf.custom_config.CUSTOM_COBBLE_CATALYSTS)) {
                if (LibTools.getStateFromString(catalyst) == null) {
                    LibMeta.LOG.warn("Failed to add catalyst from: " + catalyst);
                } else {
                    IBlockState block = LibTools.getStateFromString(catalyst);
                    if (!block.getBlock().equals(Blocks.AIR)) {
                        customCobbleCatalysts.add(block);
                        if(Conf.custom_config.DEBUG_MESSAGES) { LibMeta.LOG.info("Added custom cobble catalyst: " + block.toString()); }
                    } else {
                        LibMeta.LOG.warn("Unable to get block from string. Check if namespace and path are spelled correctly or meta is right");
                        LibMeta.LOG.warn("Failed to add catalyst from: " + catalyst);
                    }
                }
            }
        }

        if (Conf.base_config.VANILLA_VARIANTS_AS_STONE) {
            customStoneCatalysts.add(LibTools.getStateFromString("minecraft:stone/1"));
            customStoneCatalysts.add(LibTools.getStateFromString("minecraft:stone/3"));
            customStoneCatalysts.add(LibTools.getStateFromString("minecraft:stone/5"));
        }
        if (Conf.base_config.VANILLA_VARIANTS_AS_COBBLE) {
            customCobbleCatalysts.add(LibTools.getStateFromString("minecraft:stone/1"));
            customCobbleCatalysts.add(LibTools.getStateFromString("minecraft:stone/3"));
            customCobbleCatalysts.add(LibTools.getStateFromString("minecraft:stone/5"));
        }

        if(Loader.isModLoaded("quark") && Conf.compat_config.ENABLE_QUARK_COMPAT && Conf.compat_config.QUARK_VARIANTS_AS_STONE) {
            customStoneCatalysts.add(LibTools.getStateFromString("quark:marble"));
            customStoneCatalysts.add(LibTools.getStateFromString("quark:limestone"));
            customStoneCatalysts.add(LibTools.getStateFromString("quark:jasper"));
            customStoneCatalysts.add(LibTools.getStateFromString("quark:slate"));
            customStoneCatalysts.add(LibTools.getStateFromString("quark:basalt"));
        }

        if(Loader.isModLoaded("quark") && Conf.compat_config.ENABLE_QUARK_COMPAT && Conf.compat_config.QUARK_VARIANTS_AS_COBBLE) {
            customCobbleCatalysts.add(LibTools.getStateFromString("quark:marble"));
            customCobbleCatalysts.add(LibTools.getStateFromString("quark:limestone"));
            customCobbleCatalysts.add(LibTools.getStateFromString("quark:jasper"));
            customCobbleCatalysts.add(LibTools.getStateFromString("quark:slate"));
            customCobbleCatalysts.add(LibTools.getStateFromString("quark:basalt"));
        }
    }

    public static void registerJEIRecipes() {
        ItemStack noCatalyst = new ItemStack(Blocks.BARRIER).setStackDisplayName("No Catalyst");
        new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "default_stonegen"), noCatalyst, new ItemStack(Blocks.STONE)).registerStone();
        new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, "default_cobblegen"), noCatalyst, new ItemStack(Blocks.COBBLESTONE)).registerCobblestone();

        if(!StoneGen.customStoneCatalysts.isEmpty()) {
            int count = 0;
            for(IBlockState state : StoneGen.customStoneCatalysts) {
                count++;
                Block block = state.getBlock();
                String resource_path = block.getRegistryName().getResourceDomain() + "_" + block.getRegistryName().getResourcePath() + "_stonegen" + count;
                new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, resource_path), new ItemStack(block, 1, block.getMetaFromState(state))).registerStone();
            }
        }

        if(!StoneGen.customCobbleCatalysts.isEmpty()) {
            int count = 0;
            for(IBlockState state : StoneGen.customCobbleCatalysts) {
                count++;
                Block block = state.getBlock();
                String resource_path = block.getRegistryName().getResourceDomain() + "_" + block.getRegistryName().getResourcePath() + "_cobblegen" + count;
                new StoneCatalystRecipe(new ResourceLocation(LibMeta.MOD_ID, resource_path), new ItemStack(block, 1, block.getMetaFromState(state))).registerCobblestone();
            }
        }
    }
}
