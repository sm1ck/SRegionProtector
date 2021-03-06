package Sergey_Dertan.SRegionProtector.Provider;

import Sergey_Dertan.SRegionProtector.Main.SRegionProtectorMain;
import Sergey_Dertan.SRegionProtector.Region.Chunk.Chunk;
import Sergey_Dertan.SRegionProtector.Region.Region;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class YAMLProvider extends Provider {

    public YAMLProvider(PluginLogger logger) {
        super(logger);
    }

    @Override
    public String getName() {
        return "YAML";
    }

    @Override
    public Set<Map<String, Object>> loadChunkList() {
        Set<Map<String, Object>> list = new HashSet<>();
        for (File file : (new File(SRegionProtectorMain.SRegionProtectorChunksFolder).listFiles())) {
            if (file.isDirectory()) continue;
            list.add((Map<String, Object>) new Config(file.getAbsolutePath(), Config.YAML).get("data"));
        }
        return list;
    }

    @Override
    public Set<Map<String, Object>> loadRegionList() {
        //TODO try catch data loading
        Set<Map<String, Object>> list = new HashSet<>();
        for (File file : (new File(SRegionProtectorMain.SRegionProtectorRegionsFolder).listFiles())) {
            if (file.isDirectory()) continue;
            Object o = new Config(file.getAbsolutePath(), Config.YAML).get("data");
            if (o == null) {
                this.logger.alert(TextFormat.RED + "Error while loading region from file " + file.getName());
                continue;
            }
            list.add((Map<String, Object>) o);
        }
        return list;
    }

    @Override
    public Map<String, Map<String, Object>> loadFlags(String region) {
        return (Map<String, Map<String, Object>>) (new Config(SRegionProtectorMain.SRegionProtectorFlagsFolder + region + ".yml").get("data"));
    }

    @Override
    public synchronized void saveChunkList(Map<String, Map<Long, Chunk>> chunks) {
        File dir = new File(SRegionProtectorMain.SRegionProtectorChunksFolder);
        File[] files = dir.listFiles();
        for (File file : files) {
            file.delete();
        }
        super.saveChunkList(chunks);
    }

    @Override
    public synchronized void saveChunk(Chunk chunk, String level) {
        try {
            synchronized (chunk.lock) {
                Config file = new Config(SRegionProtectorMain.SRegionProtectorChunksFolder + level + "." + chunk.getHash() + ".yml", Config.YAML);
                Map<String, Object> data = chunk.toMap();
                data.put("level", level);
                file.set("data", data);
                file.save();
            }
        } catch (RuntimeException e) {
            this.logger.warning(TextFormat.YELLOW + "Cant save chunk(x: " + chunk.getX() + ", z: " + chunk.getZ() + ", level: " + level + ": " + e.getMessage());
        }
    }

    @Override
    public synchronized void saveFlags(Region region) {
        synchronized (region.lock) {
            Config file = new Config(SRegionProtectorMain.SRegionProtectorFlagsFolder + region.getName() + ".yml", Config.YAML);
            file.set("data", region.flagsToMap());
            file.save();
        }
    }

    @Override
    public synchronized void saveRegion(Region region) {
        try {
            synchronized (region.lock) {
                Config file = new Config(SRegionProtectorMain.SRegionProtectorRegionsFolder + region.getName() + ".yml", Config.YAML);
                file.set("data", region.toMap());
                file.save();
                this.saveFlags(region);
            }
        } catch (RuntimeException e) {
            this.logger.warning(TextFormat.YELLOW + "Cant save region " + region.getName() + ": " + e.getMessage());
        }
    }

    @Override
    public void removeChunk(Chunk chunk, String level) {
        new File(SRegionProtectorMain.SRegionProtectorChunksFolder + level + "." + chunk.getHash() + ".yml").delete();
    }

    @Override
    public void removeRegion(Region region) {
        new File(SRegionProtectorMain.SRegionProtectorRegionsFolder + region.getName() + ".yml").delete();
        new File(SRegionProtectorMain.SRegionProtectorFlagsFolder + region.getName() + ".yml").delete();
    }
}