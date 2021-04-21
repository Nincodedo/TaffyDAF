package com.gerflargle.taffy;

import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IWorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.StringUtils;
import sereneseasons.handler.season.SeasonHandler;
import sereneseasons.season.SeasonSavedData;
import sereneseasons.season.SeasonTime;

@Mod.EventBusSubscriber(modid=DAF.MODID, value={Dist.DEDICATED_SERVER})
public class Description {

    static MinecraftServer mcServer;
    static ServerWorld world ;
    static boolean ready;

    @SubscribeEvent
    public static void modifyDescription(TickEvent.ServerTickEvent event) {
        if(ready) {
            String newDescription = "";
            if (null != world) {
                IWorldInfo worldInfo =  world.getWorldInfo();
                String upTime = "  \u00a79" + Config.labelDate + "\u00a7f";
                String time = "  \u00a73" + Config.labelTime;
                String weather = "  \u00a76" + Config.labelWeather;
                String season = "  \u00A7f" + Config.labelSeason;

                if (Config.enableDate) {
                    int dayCount = (int)(worldInfo.getGameTime() / 24000L % Integer.MAX_VALUE);
                    int years = (int)Math.floor((double)dayCount / 365.0);
                    int days = dayCount % 365;
                    if (years > 0) {
                        upTime = upTime + years + Config.labelYears;
                    }
                    upTime = upTime + days + Config.labelDays;
                    newDescription = newDescription + upTime;
                }
                if (Config.enableTime) {
                    int hour;
                    String ampm = Config.labelMorning;
                    String timeColor = "\u00a72";
                    long currentTime = worldInfo.getDayTime() % 24000L;
                    if (currentTime >= 12000L) {
                        timeColor = "\u00a74";
                    }
                    if (currentTime >= 6000L && currentTime < 18000L) {
                        ampm = Config.labelAfternoon;
                    }
                    if ((hour = (int)Math.floor(currentTime) / 1000 + 6) == 0 || hour == 24) {
                        hour = 12;
                    }
                    if (hour != 12) {
                        hour %= 12;
                    }
                    String minutes = StringUtils.right(("0" + currentTime % 1000L * 60L / 1000L), 2);
                    time = time + timeColor + hour + ":" + minutes + ampm;
                    newDescription = newDescription + time;
                }
                if (Config.enableWeather) {
                    weather = worldInfo.isThundering() ? weather + "\u00a78" + Config.labelThundering : (worldInfo.isRaining() ? weather + "\u00a79" + Config.labelRaining : weather + "\u00a7e" + Config.labelClear);
                    newDescription = newDescription + weather;
                }
                newDescription = StringUtils.trim(newDescription);

                if (Config.enableSeasons) {
                    SeasonSavedData seasonData = SeasonHandler.getSeasonSavedData(world);
                    SeasonTime seasonTime = new SeasonTime(seasonData.seasonCycleTicks);
                    String currentSeason = seasonTime.getSeason().toString();
                    switch(currentSeason) {
                        case "SPRING":
                            season += "\u00A7a" + Config.labelSpring;
                            break;
                        case "SUMMER":
                            season += "\u00A7e" + Config.labelSummer;
                            break;
                        case "AUTUMN":
                            season += "\u00A76" + Config.labelFall;
                            break;
                        case "WINTER":
                            season += "\u00A77" + Config.labelWinter;
                            break;
                    }

                    newDescription = newDescription + season;
                }
                newDescription = StringUtils.trim(newDescription);

            } else {
                newDescription = "\u00a74There was an error fetching Dim " + Config.dimName;
            }
            if(!StringUtils.isEmpty(newDescription)) {
                ServerStatusResponse rsp = mcServer.getServerStatusResponse();
                rsp.setServerDescription(new StringTextComponent( mcServer.getMOTD() + " \n" + newDescription));
            } else {
                DAF.LOGGER.info("DAF was empty or null");
            }
        }
    }
}
