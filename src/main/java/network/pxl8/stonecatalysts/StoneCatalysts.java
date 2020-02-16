package network.pxl8.stonecatalysts;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import network.pxl8.stonecatalysts.config.Configuration;
import network.pxl8.stonecatalysts.lib.LibMeta;

@Mod("stonecatalysts")
public class StoneCatalysts {
    public StoneCatalysts() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);
        Configuration.loadConfig(Configuration.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("stonecatalysts-common.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
        LibMeta.LOG.debug("StoneCatalysts");
    }
}
