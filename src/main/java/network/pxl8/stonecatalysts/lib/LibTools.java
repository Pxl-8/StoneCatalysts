package network.pxl8.stonecatalysts.lib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class LibTools {
    public static IBlockState getStateFromString(String block) {
        String[] blockSplit = block.split("/");
        ResourceLocation id = new ResourceLocation(blockSplit[0]);
        int meta;
        IBlockState blockState;

        if (blockSplit.length > 2) {
            LibMeta.LOG.warn("Improperly formatted custom catalyst, please check your config. Proper syntax is <namespace:path/meta>. Too many arguments found");
            return null;
        }

        if (blockSplit.length == 2) {
            try { meta = Integer.parseInt(blockSplit[1]);
            } catch (NumberFormatException e) {
                LibMeta.LOG.warn("Improperly formatted custom catalyst, please check your config. Proper syntax is <namespace:path/meta>. Meta needs to be an integer");
                return null;
            }
            blockState = ForgeRegistries.BLOCKS.getValue(id).getStateFromMeta(meta);
        } else {
            blockState = ForgeRegistries.BLOCKS.getValue(id).getDefaultState();
        }

        return blockState;
    }
}
