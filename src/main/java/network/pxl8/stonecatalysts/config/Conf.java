package network.pxl8.stonecatalysts.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import network.pxl8.stonecatalysts.lib.LibMeta;

@Config(modid = LibMeta.MOD_ID, name = LibMeta.MOD_ID)
public class Conf {
    public static BaseConf base_config = new BaseConf();
    public static CompatConf compat_config = new CompatConf();

    public static class BaseConf {
        @Config.Comment({"Replace stone generation (Lava over Water) with the corresponding catalyst"})
        public Boolean REPLACE_STONE = true;
        @Config.Comment({"Replace cobble generation (Water into Flowing Lava) with the corresponding catalyst"})
        public Boolean REPLACE_COBBLE = true;
    }

    public static class CompatConf {
        @Config.Comment({"Adds quark stones (Basalt, Marble, Limestone, Slate, Jasper) as catalysts"})
        public Boolean  QUARK_COMPAT = true;
    }

    @Mod.EventBusSubscriber
    public static class ConfigSync {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
            if (event.getModID().equals(LibMeta.MOD_ID)) { ConfigManager.sync(LibMeta.MOD_ID, Config.Type.INSTANCE); }
        }
    }
}
