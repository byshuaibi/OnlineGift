package cc.mcwarzone.command;

import cc.mcwarzone.OnlineGift_Server;
import cc.mcwarzone.message.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GiftsCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("cyog")){
            if(sender.isOp()){
                if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
                    OnlineGift_Server.update();
                    sender.sendMessage(MessageUtils.getMessage("Prefix") + "Reloaded.");
                    return true;
                }
            }
        }
        return true;
    }
}
