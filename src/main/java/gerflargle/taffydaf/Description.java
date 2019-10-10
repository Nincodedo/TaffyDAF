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
    @SubscribeEvent
    public static void modifyDescription(TickEvent.ServerTickEvent event) {
        MinecraftServer mcServer = ServerLifecycleHooks.getCurrentServer();
        int dimID = Config.dimID.get();
        DimensionType dim = DimensionType.getById(dimID);

        String newDescription = "\u00A79Up: \u00A7f%s  \u00A73Time: %s  \u00A76Weather: \u00A7f%s";
        if(dim != null) {
            WorldInfo worldInfo = mcServer.getWorld(dim).getWorldInfo();
            String upTime = "";
            int dayCount = (int) (worldInfo.getDayTime() / 24000L % 2147483647L);
            int years = (int) Math.floor(dayCount / 365d);
            int days = dayCount % 365;
            if(years > 0) {
                upTime = years +"y " + upTime;
            }
            upTime = upTime + days + "d";
            
            String ampm = " AM";
            String timeColor = "\u00A72";
            long currentTime = worldInfo.getDayTime() % 24000L;
            if(currentTime >= 12000) { timeColor = "\u00A74"; }
            if(currentTime >= 6000 && currentTime < 18000) { ampm = " PM"; }
            int hour = ((int) Math.floor(currentTime) / 1000) + 6;
            if(hour == 0 || hour == 24) { hour = 12; }
            if(hour != 12) { hour = hour % 12; }
            String minutes = StringUtils.right(("0" + (currentTime % 1000) * 60 / 1000),2);
            String time = timeColor + hour + ":" + minutes + ampm;
            String weather = "\u00A7eClear";
            if(worldInfo.isRaining()) { weather = "\u00A79Rain"; }
            if(worldInfo.isThundering()) { weather = "\u00A78Thundering"; }
            newDescription = String.format(newDescription,upTime,time,weather);
        } else {
            newDescription = "\u00A74There was an error fetching Dim " + dimID;
        }
        mcServer.getServerStatusResponse().setServerDescription(new StringTextComponent(mcServer.getMOTD() + "\n" + newDescription));
    }
}
