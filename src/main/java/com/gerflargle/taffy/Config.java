package com.gerflargle.taffy;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = DAF.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
class Config {
    static final String CATEGORY_GENERAL = "General";
    static final String CATEGORY_FEATURE = "Feature";
    static final String CATEGORY_DISPLAY = "Display";

    static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static String dimName;
    static Boolean enableDate;
    static String labelDate;
    static String labelYears;
    static String labelDays;
    static Boolean enableTime;
    static String labelTime;
    static String labelMorning;
    static String labelAfternoon;
    static Boolean enableWeather;
    static String labelWeather;
    static String labelThundering;
    static String labelRaining;
    static String labelClear;

    static Boolean enableSeasons;
    static String labelSeason;
    static String labelSpring;
    static String labelSummer;
    static String labelFall;
    static String labelWinter;

    static {
        Pair specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = (ForgeConfigSpec)specPair.getRight();
        SERVER = (ServerConfig) specPair.getLeft();
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == SERVER_SPEC) {
            bakeConfig();
        }
    }

    public static void bakeConfig() {
        dimName = SERVER.dimName.get();
        enableDate = SERVER.enableDate.get();
        labelDate = SERVER.labelDate.get();
        enableTime = SERVER.enableTime.get();
        labelTime = SERVER.labelTime.get();
        labelYears = SERVER.labelYears.get();
        labelDays = SERVER.labelDays.get();
        labelMorning = SERVER.labelMorning.get();
        labelAfternoon = SERVER.labelAfternoon.get();
        enableWeather = SERVER.enableWeather.get();
        labelWeather = SERVER.labelWeather.get();
        labelThundering = SERVER.labelThundering.get();
        labelRaining = SERVER.labelRaining.get();
        labelClear = SERVER.labelClear.get();
        enableSeasons = SERVER.enableSeasons.get();
        labelSeason = SERVER.labelSeason.get();
        labelSpring = SERVER.labelSpring.get();
        labelSummer = SERVER.labelSummer.get();
        labelFall = SERVER.labelFall.get();
        labelWinter = SERVER.labelWinter.get();
    }

    public static class ServerConfig {
        final ForgeConfigSpec.ConfigValue<String> dimName;
        final ForgeConfigSpec.ConfigValue<Boolean> enableDate;
        final ForgeConfigSpec.ConfigValue<String> labelDate;
        final ForgeConfigSpec.ConfigValue<String> labelYears;
        final ForgeConfigSpec.ConfigValue<String> labelDays;
        final ForgeConfigSpec.ConfigValue<Boolean> enableTime;
        final ForgeConfigSpec.ConfigValue<String> labelTime;
        final ForgeConfigSpec.ConfigValue<String> labelMorning;
        final ForgeConfigSpec.ConfigValue<String> labelAfternoon;
        final ForgeConfigSpec.ConfigValue<Boolean> enableWeather;
        final ForgeConfigSpec.ConfigValue<String> labelWeather;
        final ForgeConfigSpec.ConfigValue<String> labelThundering;
        final ForgeConfigSpec.ConfigValue<String> labelRaining;
        final ForgeConfigSpec.ConfigValue<String> labelClear;

        final ForgeConfigSpec.ConfigValue<Boolean> enableSeasons;
        final ForgeConfigSpec.ConfigValue<String> labelSeason;
        final ForgeConfigSpec.ConfigValue<String> labelSpring;
        final ForgeConfigSpec.ConfigValue<String> labelSummer;
        final ForgeConfigSpec.ConfigValue<String> labelFall;
        final ForgeConfigSpec.ConfigValue<String> labelWinter;

        ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("General settings").push(CATEGORY_GENERAL);
            this.dimName = builder.comment("Dimension to use for weather displayed on the 'Join Multiplayer' screen.").define("dimName", "minecraft:overworld");
            builder.pop();
            builder.comment("Feature settings").push(CATEGORY_FEATURE);
            this.enableDate = builder.comment("Should we show the Date? [True/False]").define("enableDate", true);
            this.enableTime = builder.comment("Should we show the Time? [True/False]").define("enableTime", true);
            this.enableWeather = builder.comment("Should we show the Weather? [True/False]").define("enableWeather", true);
            this.enableSeasons = builder.comment("Should we show the Season? [True/False]").define("enableSeasons", true);
            builder.pop();
            builder.comment("Display settings").push(CATEGORY_DISPLAY);
            this.labelDate = builder.comment("What should we show as the Date text?").define("labelDate", "Date: ");
            this.labelYears = builder.comment("What should we show as the Years text?").define("labelYears", "y ");
            this.labelDays = builder.comment("What should we show as the Day text?").define("labelDays", "d");
            this.labelTime = builder.comment("What should we show as the Time text?").define("labelTime", "Time: ");
            this.labelMorning = builder.comment("What should we show as the Morning text?").define("labelMorning", " AM");
            this.labelAfternoon = builder.comment("What should we show as the Afternoon text?").define("labelAfternoon", " PM");
            this.labelWeather = builder.comment("What should we show as the Weather text?").define("labelWeather", "Weather: ");
            this.labelThundering = builder.comment("What should we show as the Thundering text?").define("labelThundering", "Thundering");
            this.labelRaining = builder.comment("What should we show as the Raining text?").define("labelRaining", "Rain");
            this.labelClear = builder.comment("What should we show as the Clear text?").define("labelClear", "Clear");

            this.labelSeason = builder.comment("What should we show as the Season text?").define("labelSeason", "Season: ");
            this.labelSpring = builder.comment("What should we show as the Spring text?").define("labelSpring", "Spring");
            this.labelSummer = builder.comment("What should we show as the Summer text?").define("labelSummer", "Summer");
            this.labelFall = builder.comment("What should we show as the Fall text?").define("labelFall", "Fall");
            this.labelWinter = builder.comment("What should we show as the Winter text?").define("labelWinter", "Winter");
            builder.pop();
        }
    }
}
