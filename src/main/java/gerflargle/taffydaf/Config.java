package gerflargle.taffydaf;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber
class Config {
    private static final String CATEGORY_GENERAL = "General";
    private static final String CATEGORY_DISPLAY = "Display";

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    static ForgeConfigSpec SERVER_CONFIG;
    static final ForgeConfigSpec.ConfigValue<Integer> dimID;
    static final ForgeConfigSpec.ConfigValue<Boolean> enableDate;
    static final ForgeConfigSpec.ConfigValue<Boolean> enableTime;
    static final ForgeConfigSpec.ConfigValue<Boolean> enableWeather;

    static {
        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        dimID = SERVER_BUILDER
                .comment("[int] Dimension ID to display on the 'Join Multiplayer' screen.")
                .define("dimID",0);
        SERVER_BUILDER.pop();
        SERVER_BUILDER.comment("Display settings").push(CATEGORY_DISPLAY);
        enableDate = SERVER_BUILDER
                .comment("Should we show the Date? [True/False]")
                .define("enableDate",true);
        enableTime = SERVER_BUILDER
                .comment("Should we show the Time? [True/False]")
                .define("enableTime", true);
        enableWeather = SERVER_BUILDER
                .comment("Should we show the Weather? [True/False]")
                .define("enableWeather", true);
        SERVER_BUILDER.pop();
        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }
}
