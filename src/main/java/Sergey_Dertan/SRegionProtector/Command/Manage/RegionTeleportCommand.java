package Sergey_Dertan.SRegionProtector.Command.Manage;

import Sergey_Dertan.SRegionProtector.Command.SRegionProtectorCommand;
import Sergey_Dertan.SRegionProtector.Region.Region;
import Sergey_Dertan.SRegionProtector.Region.RegionManager;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import static Sergey_Dertan.SRegionProtector.Region.Flags.RegionFlags.FLAG_TELEPORT;

public final class RegionTeleportCommand extends SRegionProtectorCommand {

    private RegionManager regionManager;

    public RegionTeleportCommand(String name, RegionManager regionManager) {
        super(name);
        this.regionManager = regionManager;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!this.testPermissionSilent(sender)) {
            this.messenger.sendMessage(sender, "command.teleport.permission");
            return false;
        }
        if (!(sender instanceof Player)) {
            this.messenger.sendMessage(sender, "command.teleport.in-game");
            return false;
        }
        if (args.length < 1) {
            this.messenger.sendMessage(sender, "command.teleport.usage");
            return false;
        }
        Region region = this.regionManager.getRegion(args[0]);
        if (region == null) {
            this.messenger.sendMessage(sender, "command.teleport.region-doesnt-exists");
            return false;
        }
        if (!sender.hasPermission("sregionprotector.admin") && !region.isLivesIn(sender.getName().toLowerCase())) {
            this.messenger.sendMessage(sender, "command.teleport.permission");
            return false;
        }
        if (!region.getFlagState(FLAG_TELEPORT) || region.getTeleportFlagPos() == null) {
            this.messenger.sendMessage(sender, "command.teleport.teleport-disabled");
            return false;
        }
        ((Player) sender).teleport(region.getTeleportFlagPos());
        this.messenger.sendMessage(sender, "command.teleport.teleport", "@region", region.getName());
        return true;
    }
}