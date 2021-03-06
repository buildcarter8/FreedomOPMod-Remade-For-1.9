package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TFM_Sync;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SENIOR, source = SourceType.BOTH)
@CommandParameters(
        description = "Same as normal adminchat but for seniors.",
        usage = "/<command> [message...]",
        aliases = "senioradminchat")
public class Command_p extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            if (senderIsConsole)
            {
                sender.sendMessage(ChatColor.RED + "Only in-game players can toggle Senior AdminChat.");
                return true;
            }

            TFM_PlayerData userinfo = TFM_PlayerData.getPlayerData(sender_p);
            
            if (userinfo.inAdminChat()) {
                userinfo.setAdminChat(!userinfo.inAdminChat());
            }
            userinfo.setSeniorAdminChat(!userinfo.inSeniorAdminChat());
            sender.sendMessage(ChatColor.GRAY + "Toggled Senior Admin Chat " + (userinfo.inSeniorAdminChat() ? "on" : "off") + ".");
        }
        else
        {
            TFM_Sync.seniorAdminChatMessage(sender, StringUtils.join(args, " "), senderIsConsole);
        }

        return true;
    }
}
