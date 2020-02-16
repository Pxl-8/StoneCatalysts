package network.pxl8.stonecatalysts.event;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import network.pxl8.stonecatalysts.config.Conf;
import network.pxl8.stonecatalysts.lib.LibTools;

@Mod.EventBusSubscriber
public class StoneGen {
    @SubscribeEvent
    public static void genStone(BlockEvent.FluidPlaceBlockEvent event) {
        BlockPos pos = event.getLiquidPos();
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        Block oldBlock = event.getOriginalState().getBlock();
        Block newBlock = event.getNewState().getBlock();

        if (Conf.base_config.REPLACE_STONE && oldBlock.equals(Blocks.WATER) && newBlock.equals(Blocks.STONE)) {
            IBlockState catalyst = event.getWorld().getBlockState(new BlockPos(x, y - 2, z));
            doReplacements(event, catalyst);
        }

        if (Conf.base_config.REPLACE_COBBLE && oldBlock.equals(Blocks.FLOWING_LAVA) && newBlock.equals(Blocks.COBBLESTONE)) {
            IBlockState catalyst = event.getWorld().getBlockState(new BlockPos(x, y - 1, z));
            doReplacements(event, catalyst);
        }
    }

    private static void replaceBlock(BlockEvent.FluidPlaceBlockEvent event, IBlockState catalyst, String replace) {
        if (catalyst.equals(LibTools.getStateFromString(replace))) { event.setNewState(LibTools.getStateFromString(replace)); }
    }

    private static void doReplacements(BlockEvent.FluidPlaceBlockEvent event, IBlockState catalyst) {
        replaceBlock(event, catalyst, "minecraft:stone/1");
        replaceBlock(event, catalyst, "minecraft:stone/3");
        replaceBlock(event, catalyst, "minecraft:stone/5");

        if(Loader.isModLoaded("quark") && Conf.compat_config.QUARK_COMPAT) {
            replaceBlock(event, catalyst, "quark:basalt");
            replaceBlock(event, catalyst, "quark:marble");
            replaceBlock(event, catalyst, "quark:limestone");
            replaceBlock(event, catalyst, "quark:slate");
            replaceBlock(event, catalyst, "quark:jasper");
        }
    }
}
