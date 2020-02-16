package network.pxl8.stonecatalysts.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import network.pxl8.stonecatalysts.lib.LibMeta;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Configuration {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue REPLACE_STONE;
    public static ForgeConfigSpec.BooleanValue REPLACE_COBBLE;

    public static ForgeConfigSpec.BooleanValue ENABLE_QUARK_COMPAT;

    static {
        COMMON_BUILDER.push("base_config");
        setupBaseConfig();
        COMMON_BUILDER.push("compat_config");
        setupCompatConfig();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    private static void setupBaseConfig() {
        REPLACE_STONE = COMMON_BUILDER.comment("Replace stone generation (Lava over Water) with the corresponding catalyst")
                .define("REPLACE_STONE", true);
        REPLACE_COBBLE = COMMON_BUILDER.comment("Replace cobble generation (Water into Flowing Lava) with the corresponding catalyst")
                .define("REPLACE_COBBLE", true);
        COMMON_BUILDER.pop();
    }

    private static void setupCompatConfig() {
        ENABLE_QUARK_COMPAT = COMMON_BUILDER.comment("Adds quark stones (Basalt, Marble, Limestone, Slate, Jasper) as catalysts")
                .define("ENABLE_QUARK_COMPAT", true);
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
