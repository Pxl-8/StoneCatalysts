package network.pxl8.stonecatalysts.event;

import net.minecraft.block.Block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
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

        if (Configuration.REPLACE_STONE.get() && oldBlock.equals(Blocks.WATER) && newBlock.equals(Blocks.STONE)) {
            BlockState catalyst = event.getWorld().getBlockState(new BlockPos(x, y - 1, z));
            doReplacements(event, catalyst);
        }

        if (Configuration.REPLACE_COBBLE.get() && oldBlock.equals(Blocks.LAVA) && newBlock.equals(Blocks.COBBLESTONE)) {
            BlockState catalyst = event.getWorld().getBlockState(new BlockPos(x, y - 1, z));
            doReplacements(event, catalyst);
        }
    }

    private static void replaceBlock(BlockEvent.FluidPlaceBlockEvent event, BlockState catalyst, BlockState replace) {
        if (catalyst.equals(replace)) { event.setNewState(replace); }
    }

    private static void doReplacements(BlockEvent.FluidPlaceBlockEvent event, BlockState catalyst) {
        replaceBlock(event, catalyst, Blocks.GRANITE.getDefaultState());
        replaceBlock(event, catalyst, Blocks.DIORITE.getDefaultState());
        replaceBlock(event, catalyst, Blocks.ANDESITE.getDefaultState());

        //if(ModList.get().isLoaded("quark") && Configuration.ENABLE_QUARK_COMPAT.get()) {
        //    replaceBlock(event, catalyst, NewStoneTypesModule.marbleBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.limestoneBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.jasperBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.slateBlock.getDefaultState());
        //    replaceBlock(event, catalyst, NewStoneTypesModule.basaltBlock.getDefaultState());
        //}

        if(!StoneGen.customCatalysts.isEmpty()) {
            for(BlockState block : StoneGen.customCatalysts) {
                replaceBlock(event, catalyst, block);
            }
        }
    }

    private static List<BlockState> customCatalysts = new ArrayList<>();

    public static void getCustomCatalysts() {
        if(!Configuration.CUSTOM_CATALYSTS.get().isEmpty()) {
            for(String catalyst : Configuration.CUSTOM_CATALYSTS.get()) {
                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(catalyst));
                if(!block.equals(Blocks.AIR)) {
                    customCatalysts.add(block.getDefaultState());
                    if(Configuration.DEBUG_MESSAGES.get()) { LibMeta.LOG.debug("Added custom catalyst: " + block); }
                } else {
                    LibMeta.LOG.warn("Could not find catalyst from namespaced id: " + catalyst);
                }
            }
        }
    }
}
