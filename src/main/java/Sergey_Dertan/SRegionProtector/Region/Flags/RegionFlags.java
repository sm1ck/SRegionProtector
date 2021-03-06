package Sergey_Dertan.SRegionProtector.Region.Flags;

import Sergey_Dertan.SRegionProtector.Region.Flags.Flag.RegionFlag;
import Sergey_Dertan.SRegionProtector.Region.Flags.Flag.RegionSellFlag;
import Sergey_Dertan.SRegionProtector.Region.Flags.Flag.RegionTeleportFlag;
import Sergey_Dertan.SRegionProtector.Utils.Utils;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.permission.Permissible;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.PluginManager;

import java.util.Arrays;
import java.util.Map;

public abstract class RegionFlags {

    public static final int FLAG_INVALID = -1;
    public static final int FLAG_BUILD = 0;
    public static final int FLAG_INTERACT = 1;
    public static final int FLAG_USE = 2;
    public static final int FLAG_PVP = 3;
    public static final int FLAG_EXPLODE = 4;
    public static final int FLAG_LIGHTER = 5;
    public static final int FLAG_MAGIC_ITEM_USE = 6;
    public static final int FLAG_HEAL = 7;
    public static final int FLAG_INVINCIBLE = 8;
    public static final int FLAG_TELEPORT = 9;
    public static final int FLAG_SELL = 10;
    public static final int FLAG_POTION_LAUNCH = 11;
    public static final int FLAG_MOVE = 12;
    public static final int FLAG_LEAVES_DECAY = 13;
    public static final int FLAG_ITEM_DROP = 14;
    public static final int FLAG_SEND_CHAT = 15;
    public static final int FLAG_RECEIVE_CHAT = 16;
    public static final int FLAG_HEALTH_REGEN = 17;
    public static final int FLAG_MOB_DAMAGE = 18;
    public static final int FLAG_MOB_SPAWN = 19;

    public static final int FLAG_AMOUNT = 20;

    private static final RegionFlag[] defaults = new RegionFlag[FLAG_AMOUNT];
    private static final Permission[] permissions = new Permission[FLAG_AMOUNT];

    private RegionFlags() {
    }

    public static void init(boolean[] flagsDefault) {
        defaults[FLAG_BUILD] = new RegionFlag(flagsDefault[FLAG_BUILD]);
        defaults[FLAG_INTERACT] = new RegionFlag(flagsDefault[FLAG_INTERACT]);
        defaults[FLAG_USE] = new RegionFlag(flagsDefault[FLAG_USE]);
        defaults[FLAG_PVP] = new RegionFlag(flagsDefault[FLAG_PVP]);
        defaults[FLAG_EXPLODE] = new RegionFlag(flagsDefault[FLAG_EXPLODE]);
        defaults[FLAG_LIGHTER] = new RegionFlag(flagsDefault[FLAG_LIGHTER]);
        defaults[FLAG_MAGIC_ITEM_USE] = new RegionFlag(flagsDefault[FLAG_MAGIC_ITEM_USE]);
        defaults[FLAG_HEAL] = new RegionFlag(flagsDefault[FLAG_HEAL]);
        defaults[FLAG_INVINCIBLE] = new RegionFlag(flagsDefault[FLAG_INVINCIBLE]);
        defaults[FLAG_TELEPORT] = new RegionTeleportFlag(flagsDefault[FLAG_TELEPORT]);
        defaults[FLAG_SELL] = new RegionSellFlag(flagsDefault[FLAG_SELL]);
        defaults[FLAG_POTION_LAUNCH] = new RegionFlag(flagsDefault[FLAG_POTION_LAUNCH]);
        defaults[FLAG_MOVE] = new RegionFlag(flagsDefault[FLAG_MOVE]);
        defaults[FLAG_LEAVES_DECAY] = new RegionFlag(flagsDefault[FLAG_LEAVES_DECAY]);
        defaults[FLAG_ITEM_DROP] = new RegionFlag(flagsDefault[FLAG_ITEM_DROP]);
        defaults[FLAG_SEND_CHAT] = new RegionFlag(flagsDefault[FLAG_SEND_CHAT]);
        defaults[FLAG_RECEIVE_CHAT] = new RegionFlag(flagsDefault[FLAG_RECEIVE_CHAT]);
        defaults[FLAG_HEALTH_REGEN] = new RegionFlag(flagsDefault[FLAG_HEALTH_REGEN]);
        defaults[FLAG_MOB_DAMAGE] = new RegionFlag(flagsDefault[FLAG_MOB_DAMAGE]);
        defaults[FLAG_MOB_SPAWN] = new RegionFlag(flagsDefault[FLAG_MOB_SPAWN]);

        PluginManager pluginManager = Server.getInstance().getPluginManager();

        permissions[FLAG_BUILD] = pluginManager.getPermission("sregionprotector.region.flag.build");
        permissions[FLAG_INTERACT] = pluginManager.getPermission("sregionprotector.region.flag.interact");
        permissions[FLAG_USE] = pluginManager.getPermission("sregionprotector.region.flag.use");
        permissions[FLAG_PVP] = pluginManager.getPermission("sregionprotector.region.flag.pvp");
        permissions[FLAG_EXPLODE] = pluginManager.getPermission("sregionprotector.region.flag.explode");
        permissions[FLAG_LIGHTER] = pluginManager.getPermission("sregionprotector.region.flag.lighter");
        permissions[FLAG_MAGIC_ITEM_USE] = pluginManager.getPermission("sregionprotector.region.flag.magic_item_use");
        permissions[FLAG_HEAL] = pluginManager.getPermission("sregionprotector.region.flag.heal");
        permissions[FLAG_INVINCIBLE] = pluginManager.getPermission("sregionprotector.region.flag.invincible");
        permissions[FLAG_TELEPORT] = pluginManager.getPermission("sregionprotector.region.flag.teleport");
        permissions[FLAG_SELL] = pluginManager.getPermission("sregionprotector.region.flag.sell");
        permissions[FLAG_POTION_LAUNCH] = pluginManager.getPermission("sregionprotector.region.flag.potion_launch");
        permissions[FLAG_MOVE] = pluginManager.getPermission("sregionprotector.region.flag.move");
        permissions[FLAG_LEAVES_DECAY] = pluginManager.getPermission("sregionprotector.region.flag.leaves_decay");
        permissions[FLAG_ITEM_DROP] = pluginManager.getPermission("sregionprotector.region.flag.item_drop");
        permissions[FLAG_SEND_CHAT] = pluginManager.getPermission("sregionprotector.region.flag.send_chat");
        permissions[FLAG_RECEIVE_CHAT] = pluginManager.getPermission("sregionprotector.region.flag.receive_chat");
        permissions[FLAG_HEALTH_REGEN] = pluginManager.getPermission("sregionprotector.region.flag.health_regen");
        permissions[FLAG_MOB_DAMAGE] = pluginManager.getPermission("sregionprotector.region.flag.mob_damage");
        permissions[FLAG_MOB_SPAWN] = pluginManager.getPermission("sregionprotector.region.flag.mob_spawn");
    }

    public static RegionFlag[] loadFlagList(Map<String, Map<String, Object>> data) {
        if (data == null) return getDefaultFlagList();
        RegionFlag[] flags = new RegionFlag[FLAG_AMOUNT];
        for (Map.Entry<String, Map<String, Object>> flagData : data.entrySet()) {
            int id = getFlagId(flagData.getKey());
            if (id == RegionFlags.FLAG_INVALID) continue;
            switch (id) {
                default:
                    flags[id] = new RegionFlag((boolean) flagData.getValue().get("state"));
                    break;
                case FLAG_TELEPORT:
                    flags[id] = new RegionTeleportFlag(false);
                    Map<String, Object> posData = (Map<String, Object>) flagData.getValue().get("position");
                    if (posData != null) {
                        double x = (double) posData.get("x");
                        double y = (double) posData.get("y");
                        double z = (double) posData.get("z");
                        String levelName = (String) posData.get("level");
                        if (x == 0 && y == 0 && z == 0) continue;
                        Level level = Server.getInstance().getLevelByName(levelName);
                        if (level == null) continue;
                        ((RegionTeleportFlag) flags[id]).position = new Position(x, y, z, level);
                    }
                    flags[id].state = (boolean) flagData.getValue().get("state");
                    break;
                case FLAG_SELL:
                    flags[id] = new RegionSellFlag();
                    int price = (int) flagData.getValue().getOrDefault("price", 0);
                    if (price < 0) continue;
                    ((RegionSellFlag) flags[id]).price = price;
                    flags[id].state = (boolean) flagData.getValue().get("state");
                    break;
            }
        }
        return fixMissingFlags(flags);
    }

    public static RegionFlag[] getDefaultFlagList() {
        return Utils.deepClone(Arrays.asList(defaults)).toArray(new RegionFlag[0]);
    }

    public static Permission getFlagPermission(int flag) {
        return permissions[flag];
    }

    public static String getFlagName(int flag) {
        switch (flag) {
            default:
                return "";
            case FLAG_BUILD:
                return "build";
            case FLAG_INTERACT:
                return "interact";
            case FLAG_USE:
                return "use";
            case FLAG_PVP:
                return "pvp";
            case FLAG_EXPLODE:
                return "tnt";
            case FLAG_LIGHTER:
                return "lighter";
            case FLAG_MAGIC_ITEM_USE:
                return "magicitem";
            case FLAG_HEAL:
                return "heal";
            case FLAG_INVINCIBLE:
                return "invincible";
            case FLAG_TELEPORT:
                return "teleport";
            case FLAG_SELL:
                return "sell";
            case FLAG_POTION_LAUNCH:
                return "potion_launch";
            case FLAG_MOVE:
                return "move";
            case FLAG_LEAVES_DECAY:
                return "leaves_decay";
            case FLAG_ITEM_DROP:
                return "item_drop";
            case FLAG_SEND_CHAT:
                return "send_chat";
            case FLAG_RECEIVE_CHAT:
                return "receive_chat";
            case FLAG_HEALTH_REGEN:
                return "health_regen";
            case FLAG_MOB_DAMAGE:
                return "mob-damage";
            case FLAG_MOB_SPAWN:
                return "mob-spawn";
        }
    }

    public static int getFlagId(String name) {
        switch (name) {
            default:
                return -1;
            case "build":
                return FLAG_BUILD;
            case "interact":
                return FLAG_INTERACT;
            case "use":
                return FLAG_USE;
            case "pvp":
                return FLAG_PVP;
            case "tnt":
                return FLAG_EXPLODE;
            case "lighter":
                return FLAG_LIGHTER;
            case "magic_item":
            case "magic-item":
            case "magic_item_use":
            case "magic-item-use":
            case "magicitem":
            case "magic":
                return FLAG_MAGIC_ITEM_USE;
            case "heal":
                return FLAG_HEAL;
            case "invincible":
                return FLAG_INVINCIBLE;
            case "teleport":
            case "tp":
                return FLAG_TELEPORT;
            case "sell":
                return FLAG_SELL;
            case "potion_launch":
            case "potion-launch":
                return FLAG_POTION_LAUNCH;
            case "move":
                return FLAG_MOVE;
            case "leave_decay":
            case "leave-decay":
            case "leaves-decay":
            case "leaves_decay":
                return FLAG_LEAVES_DECAY;
            case "item_drop":
            case "itemdrop":
            case "item-drop":
                return FLAG_ITEM_DROP;
            case "send-chat":
            case "sendchat":
            case "send_chat":
                return FLAG_SEND_CHAT;
            case "receive_chat":
            case "receive-chat":
            case "receivechat":
                return FLAG_RECEIVE_CHAT;
            case "health_regen":
            case "health-regen":
            case "healthregen":
                return FLAG_HEALTH_REGEN;
            case "mob-damage":
                return FLAG_MOB_DAMAGE;
            case "mob-spawn":
                return FLAG_MOB_SPAWN;
        }
    }

    public static boolean getStateFromString(String state) {
        switch (state) {
            case "yes":
            case "enable":
            case "enabled":
            case "вкл":
            case "true":
                return true;
            case "no":
            case "disable":
            case "disabled":
            case "выкл":
            case "false":
            default:
                return false;
        }
    }

    public static RegionFlag[] fixMissingFlags(RegionFlag[] flags) {
        for (int i = 0; i < FLAG_AMOUNT; ++i) {
            if (flags[i] != null) continue;
            flags[i] = defaults[i].clone();
        }
        return flags;
    }

    public static boolean hasFlagPermission(Permissible target, int flag) {
        return target.hasPermission(permissions[flag]);
    }

    public static boolean hasFlagPermission(Permissible target, String flag) {
        return hasFlagPermission(target, getFlagId(flag));
    }

    public static boolean getDefaultFlagState(int flag) {
        return defaults[flag].state;
    }

    public static boolean getDefaultFlagState(String flag) {
        return getDefaultFlagState(getFlagId(flag));
    }
}