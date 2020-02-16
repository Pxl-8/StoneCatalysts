package network.pxl8.stonecatalysts.event;

import net.minecraft.block.Block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import network.pxl8.stonecatalysts.config.Configuration;
import network.pxl8.stonecatalysts.lib.LibMeta;
import vazkii.quark.world.module.NewStoneTypesModule;


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

        if(ModList.get().isLoaded("quark") && Configuration.ENABLE_QUARK_COMPAT.get()) {
            replaceBlock(event, catalyst, NewStoneTypesModule.marbleBlock.getDefaultState());
            replaceBlock(event, catalyst, NewStoneTypesModule.limestoneBlock.getDefaultState());
            replaceBlock(event, catalyst, NewStoneTypesModule.jasperBlock.getDefaultState());
            replaceBlock(event, catalyst, NewStoneTypesModule.slateBlock.getDefaultState());
            replaceBlock(event, catalyst, NewStoneTypesModule.basaltBlock.getDefaultState());
        }
    }
}
