package gerflargle.taffydaf;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.lang3.StringUtils;

@Mod.EventBusSubscriber(modid = "taffydaf", value = Dist.DEDICATED_SERVER, bus = Mod.EventBusSubscriber.Bus.FORGE)
class Description {

    static int dimID;
    static Boolean enableDate;
    static Boolean enableTime;
    static Boolean enableWeather;

    @SubscribeEvent
    public static void modifyDescription(TickEvent.ServerTickEvent event) {
        MinecraftServer mcServer = ServerLifecycleHooks.getCurrentServer();
        DimensionType dim = DimensionType.getById(dimID);

        String newDescription = "";
        if(dim != null) {
            WorldInfo worldInfo = mcServer.getWorld(dim).getWorldInfo();
            String upTime = "  \u00A79Up: \u00A7f";
            String time = "  \u00A73Time: ";
            String weather = "  \u00A76Weather: ";

            if(enableDate) {
                int dayCount = (int) (worldInfo.getDayTime() / 24000L % 2147483647L);
                int years = (int) Math.floor(dayCount / 365d);
                int days = dayCount % 365;
                if(years > 0) {
                    upTime += years +"y ";
                }
                upTime += days + "d";
                newDescription += upTime;
            }

            if(enableTime) {
                String ampm = " AM";
                String timeColor = "\u00A72";
                long currentTime = worldInfo.getDayTime() % 24000L;
                if(currentTime >= 12000) { timeColor = "\u00A74"; }
                if(currentTime >= 6000 && currentTime < 18000) { ampm = " PM"; }
                int hour = ((int) Math.floor(currentTime) / 1000) + 6;
                if(hour == 0 || hour == 24) { hour = 12; }
                if(hour != 12) { hour = hour % 12; }
                String minutes = StringUtils.right(("0" + (currentTime % 1000) * 60 / 1000),2);
                time += timeColor + hour + ":" + minutes + ampm;
                newDescription += time;
            }

            if(enableWeather) {
                if (worldInfo.isThundering()) {
                    weather += "\u00A78Thundering";
                } else if (worldInfo.isRaining()) {
                    weather += "\u00A79Rain";
                } else {
                    weather += "\u00A7eClear";
                }
                newDescription += weather;
            }
            newDescription = StringUtils.trim(newDescription);
        } else {
            newDescription = "\u00A74There was an error fetching Dim " + dimID;
        }
        mcServer.getServerStatusResponse().setServerDescription(new StringTextComponent(mcServer.getMOTD() + "\n" + newDescription));
    }
}
