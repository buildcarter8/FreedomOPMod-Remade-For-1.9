package me.StevenLawson.TotalFreedomMod.Commands;

import me.StevenLawson.TotalFreedomMod.TFM_PlayerData;
import me.StevenLawson.TotalFreedomMod.TotalFreedomMod;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "Show the last command that someone used.", usage = "/<command> <player>")
public class Command_lastcmd extends TFM_Command
{
    @Override
    public boolean run(CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        final Player player = getPlayer(args[0]);

        if (player == null)
        {
            sender.sendMessage(ChatColor.RED + TotalFreedomMod.PLAYER_NOT_FOUND);
            return true;
        }

        final TFM_PlayerData playerdata = TFM_PlayerData.getPlayerData(player);

        if (playerdata != null)
        {
            String lastCommand = playerdata.getLastCommand();
            if (lastCommand.isEmpty())
            {
                lastCommand = "(none)";
            }
            sender.sendMessage(ChatColor.GRAY + player.getName() + " - Last Command: " + lastCommand);
        }

        return true;
    }
}
