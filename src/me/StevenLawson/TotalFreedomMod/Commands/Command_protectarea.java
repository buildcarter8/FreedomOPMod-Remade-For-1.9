package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.Config.TFM_ConfigEntry;
import me.StevenLawson.TotalFreedomMod.TFM_ProtectedArea;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(
        description = "Protect areas so that only superadmins can directly modify blocks in those areas. WorldEdit and other such plugins might bypass this.",
        usage = "/<command> <list | clear | remove <label> | add <label> <radius>>")
public class Command_protectarea extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (!TFM_ConfigEntry.PROTECTAREA_ENABLED.getBoolean())
        {
            sender.sendMessage(ChatColor.RED + "Protected areas are currently disabled in the TotalFreedomMod configuration.");
            return true;
        }
        
        if (args.length == 0) {
            return false;
        }

        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list"))
            {
                sender.sendMessage(ChatColor.GRAY + "Protected Areas: " + StringUtils.join(TFM_ProtectedArea.getProtectedAreaLabels(), ", "));
            }
            else if (args[0].equalsIgnoreCase("clear"))
            {
                TFM_ProtectedArea.clearProtectedAreas();

                sender.sendMessage(ChatColor.GRAY + "Protected Areas Cleared.");
            }
            else
            {
                return false;
            }

            return true;
        }
        else if (args.length == 2)
        {
            if ("remove".equals(args[0]))
            {
                TFM_ProtectedArea.removeProtectedArea(args[1]);

                sender.sendMessage(ChatColor.GRAY + "Area removed. Protected Areas: " + StringUtils.join(TFM_ProtectedArea.getProtectedAreaLabels(), ", "));
            }
            else
            {
                return false;
            }

            return true;
        }
        else if (args.length == 3)
        {
            if (args[0].equalsIgnoreCase("add"))
            {
                if (senderIsConsole)
                {
                    sender.sendMessage(ChatColor.RED + "You must be in-game to set a protected area.");
                    return true;
                }

                Double radius;
                try
                {
                    radius = Double.parseDouble(args[2]);
                }
                catch (NumberFormatException nfex)
                {
                    sender.sendMessage(ChatColor.RED + "Invalid radius.");
                    return true;
                }

                if (radius > TFM_ProtectedArea.MAX_RADIUS || radius < 0.0D)
                {
                    sender.sendMessage(ChatColor.RED + "Invalid radius. Radius must be a positive value less than " + TFM_ProtectedArea.MAX_RADIUS + ".");
                    return true;
                }

                TFM_ProtectedArea.addProtectedArea(args[1], sender_p.getLocation(), radius);

                sender.sendMessage(ChatColor.GRAY + "Area added. Protected Areas: " + StringUtils.join(TFM_ProtectedArea.getProtectedAreaLabels(), ", "));
            }
            else
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
