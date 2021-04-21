package com.gerflargle.taffy;

import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.minecraftforge.fml.network.FMLNetworkConstants.IGNORESERVERONLY;

@Mod(DAF.MODID)
public class DAF
{
    public static final String MODID = "taffydaf";
    public static final Logger LOGGER = LogManager.getLogger();
    boolean found = false;

    public DAF() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> IGNORESERVERONLY, (a, b) -> true));
        if (FMLEnvironment.dist.isDedicatedServer()) {
            ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC, "taffydaf-server.toml");
        } else {
            LOGGER.info("TaffyDaf: Client Side Detected - Do nothing");
        }

        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("TaffyDAF is starting starting up");
        if(!ModList.get().isLoaded("sereneseasons")) {
            Config.enableSeasons = false;
        }
        Description.mcServer = ServerLifecycleHooks.getCurrentServer();
        Iterable<ServerWorld> worlds = Description.mcServer.getWorlds();

        worlds.iterator().forEachRemaining(serverWorld -> {
            String worldName = StringUtils.split(serverWorld.getWorld().getDimensionKey().toString(),'/')[1].trim();

            worldName = StringUtils.substring(worldName, 0, worldName.length() -1);
            if(!found && StringUtils.equalsIgnoreCase(Config.dimName, worldName)) {
                LOGGER.info("TaffyDaf: Setting Date and Forecast to Dim -> " + worldName );
                Description.world = serverWorld;
                found = true;
            }
        });
        if(found) {
            Description.ready = true;
        }
    }
}