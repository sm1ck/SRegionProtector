package Sergey_Dertan.SRegionProtector.Command.Manage.Group;

import Sergey_Dertan.SRegionProtector.Command.SRegionProtectorCommand;
import Sergey_Dertan.SRegionProtector.Messenger.Messenger;
import Sergey_Dertan.SRegionProtector.Region.Region;
import Sergey_Dertan.SRegionProtector.Region.RegionManager;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

import java.util.Map;

public final class RemoveMemberCommand extends SRegionProtectorCommand {

    private RegionManager regionManager;

    public RemoveMemberCommand(String name, Map<String, String> messages, RegionManager regionManager) {
        super(name, messages);
        this.regionManager = regionManager;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!this.testPermissionSilent(sender)) {
            Messenger.getInstance().sendMessage(sender, "command.removemember.permission");
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(this.usageMessage);
            return false;
        }

        Region region = this.regionManager.getRegion(args[0].toLowerCase());
        if (region == null) {
            Messenger.getInstance().sendMessage(sender, "command.removemember.region-doesnt-exists", "@region", args[0]);
            return false;
        }

        String target = args[1].toLowerCase();
        if (target.equals("")) {
            sender.sendMessage(this.usageMessage);
            return false;
        }
        if ((sender instanceof Player && !region.isOwner(sender.getName().toLowerCase())) && !sender.hasPermission("sregionprotector.admin")) {
            Messenger.getInstance().sendMessage(sender, "command.removemember.permission", "@region", args[0]);
            return false;
        }
        if (!region.isMember(target)) {
            Messenger.getInstance().sendMessage(sender, "command.removemember.not-a-member", new String[]{"@region", "@target"}, new String[]{region.getName(), target});
            return false;
        }
        this.regionManager.removeMember(region, target);
        Messenger.getInstance().sendMessage(sender, "command.removemember.member-removed", new String[]{"@region", "@target"}, new String[]{region.getName(), target});
        return true;
    }
}