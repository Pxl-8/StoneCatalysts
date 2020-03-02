package network.pxl8.stonecatalysts.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import network.pxl8.stonecatalysts.lib.LibMeta;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Configuration {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue REPLACE_STONE;
    public static ForgeConfigSpec.BooleanValue REPLACE_COBBLE;

    public static ForgeConfigSpec.BooleanValue ENABLE_QUARK_COMPAT;

    public static ForgeConfigSpec.ConfigValue<List<String>> CUSTOM_STONE_CATALYSTS;
    public static ForgeConfigSpec.ConfigValue<List<String>> CUSTOM_COBBLE_CATALYSTS;
    public static ForgeConfigSpec.BooleanValue DEBUG_MESSAGES;

    static {
        COMMON_BUILDER.push("base_config");
        setupBaseConfig();
        COMMON_BUILDER.push("compat_config");
        setupCompatConfig();
        COMMON_BUILDER.push("custom_config");
        setupCustomConfig();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void setupBaseConfig() {
        REPLACE_STONE = COMMON_BUILDER.comment("Allow vanilla stone variants as stone generator catalysts (Flowing Lava into Water Source)")
                .define("VANILLA_VARIANTS_AS_STONE", true);
        REPLACE_COBBLE = COMMON_BUILDER.comment("Allow vanilla stone variants as cobble generator catalysts (Water into Flowing Lava)")
                .define("VANILLA_VARIANTS_AS_COBBLE", true);
        COMMON_BUILDER.pop();
    }

    private static void setupCompatConfig() {
        //ENABLE_QUARK_COMPAT = COMMON_BUILDER.comment("Adds quark stones (Basalt, Marble, Limestone, Slate, Jasper) as catalysts")
        //        .define("ENABLE_QUARK_COMPAT", true);
        COMMON_BUILDER.pop();
    }

    private static void setupCustomConfig() {
        List<String> catalysts = new ArrayList<>();
        CUSTOM_STONE_CATALYSTS = COMMON_BUILDER.comment("Add additional stone generator catalysts", "Usage: Add namespaced ids in \"\" seperated by commas", "Example: [\"minecraft:netherrack\", \"quark:brimstone\"]")
                .define("CUSTOM_STONE_CATALYSTS", catalysts);
        CUSTOM_COBBLE_CATALYSTS = COMMON_BUILDER.comment("Add additional cobble generator catalysts", "Usage: Add namespaced ids in \"\" seperated by commas", "Example: [\"minecraft:netherrack\", \"quark:brimstone\"]")
                .define("CUSTOM_COBBLE_CATALYSTS", catalysts);

        DEBUG_MESSAGES = COMMON_BUILDER.comment("Prints debug messages to the log for each custom catalyst added")
                .define("DEBUG_MESSAGES", true);
        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }
}
