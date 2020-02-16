package network.pxl8.stonecatalysts.lib;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class LibTools {
    public static IBlockState getStateFromString(String block) {
        String[] blockSplit = block.split("/");
        IBlockState blockState = null;

        if (blockSplit.length == 2) {
            blockState = Block.getBlockFromName(blockSplit[0]).getStateFromMeta(Integer.parseInt(blockSplit[1]));
        } else if (blockSplit.length == 1) {
            blockState = Block.getBlockFromName(blockSplit[0]).getDefaultState();
        }

        return blockState;
    }
}
