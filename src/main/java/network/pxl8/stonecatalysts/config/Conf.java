package network.pxl8.stonecatalysts.config;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import network.pxl8.stonecatalysts.lib.LibMeta;

import java.util.ArrayList;
import java.util.List;

@Config(modid = LibMeta.MOD_ID, name = LibMeta.MOD_ID)
public class Conf {
    public static BaseConf base_config = new BaseConf();
    public static CompatConf compat_config = new CompatConf();
    public static CustomConf custom_config = new CustomConf();

    public static class BaseConf {
        @Config.Comment("Allow vanilla stone variants as stone generator catalysts (Flowing Lava into Water Source)")
        public Boolean VANILLA_VARIANTS_AS_STONE = true;
        @Config.Comment("Allow vanilla stone variants as cobble generator catalysts (Water into Flowing Lava)")
        public Boolean VANILLA_VARIANTS_AS_COBBLE = true;
    }

    public static class CompatConf {
        @Config.Comment({"Adds quark stones (Basalt, Marble, Limestone, Slate, Jasper) as catalysts"})
        public Boolean ENABLE_QUARK_COMPAT = true;

        @Config.Comment("Allow quark stone variants as stone generator catalysts (Flowing Lava into Water Source)")
        public Boolean QUARK_VARIANTS_AS_STONE = true;
        @Config.Comment("Allow quark stone variants as cobble generator catalysts (Water into Flowing Lava)")
        public Boolean QUARK_VARIANTS_AS_COBBLE = true;
    }

    public static class CustomConf {
        @Config.Comment("Add additional stone generator catalysts\n" +
                "Usage: Add namespaced ids in the format <namespace:path/meta> as new lines\n" +
                "Example: \n" +
                "<\n" +
                "   minecraft:stone/1\n" +
                "   bluepower:basalt\n" +
                ">")
        public String[] CUSTOM_STONE_CATALYSTS = new String[] {};
        @Config.Comment("Add additional stone generator catalysts\n" +
                "Usage: Add namespaced ids in the format <namespace:path/meta> as new lines\n" +
                "Example: \n" +
                "<\n" +
                "   minecraft:stone/1\n" +
                "   bluepower:basalt\n" +
                ">")
        public String[] CUSTOM_COBBLE_CATALYSTS = new String[] {};

        @Config.Comment("Prints debug messages to the log for each custom catalyst added")
        public Boolean DEBUG_MESSAGES = true;
    }

    @Mod.EventBusSubscriber
    public static class ConfigSync {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
            if (event.getModID().equals(LibMeta.MOD_ID)) { ConfigManager.sync(LibMeta.MOD_ID, Config.Type.INSTANCE); }
        }
    }
}
