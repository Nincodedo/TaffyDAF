package gerflargle.taffydaf;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("taffydaf")
public class TaffyDAF {
    private static final Logger LOGGER = LogManager.getLogger();

    public TaffyDAF() {
        if(FMLEnvironment.dist.isDedicatedServer()) {
            ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
            Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("taffydaf-server.toml"));
            LOGGER.info("Server Side Detected - Setting Date and Forecast to Dim :" + Config.dimID.get());
        } else {
            LOGGER.info("Client Side Detected - Do nothing");
        }
    }
}
