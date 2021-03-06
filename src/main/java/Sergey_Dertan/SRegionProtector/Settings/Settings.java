package Sergey_Dertan.SRegionProtector.Settings;

import Sergey_Dertan.SRegionProtector.Main.SRegionProtectorMain;
import Sergey_Dertan.SRegionProtector.Messenger.Messenger;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.Map;

import static Sergey_Dertan.SRegionProtector.Main.SRegionProtectorMain.SRegionProtectorMainFolder;
import static Sergey_Dertan.SRegionProtector.Utils.Utils.copyResource;

public final class Settings {

    public MySQLSettings mySQLSettings;
    public RegionSettings regionSettings;
    public int selectorSessionLifetime;
    public int autoSavePeriod;

    public void init(SRegionProtectorMain main) {
        try {
            copyResource("config.yml", "resources/", SRegionProtectorMainFolder, SRegionProtectorMain.class);
            copyResource("region-settings.yml", "resources/", SRegionProtectorMainFolder, SRegionProtectorMain.class);
        } catch (Exception e) {
            main.getLogger().alert(TextFormat.RED + Messenger.getInstance().getMessage("loading.error.resource", "@err", e.getMessage()));
            main.forceShutdown = true;
            main.getServer().getPluginManager().disablePlugin(main);
            return;
        }
        this.selectorSessionLifetime = (int) this.getConfig().get("session-life-time");
        this.autoSavePeriod = (int) this.getConfig().get("auto-save-period") * 20;
        this.regionSettings = new RegionSettings(this.getConfig(), new Config(SRegionProtectorMainFolder + "region-settings.yml", Config.YAML).getAll());
    }

    public Map<String, Object> getConfig() {
        return new Config(SRegionProtectorMainFolder + "config.yml", Config.YAML).getAll();
    }
}