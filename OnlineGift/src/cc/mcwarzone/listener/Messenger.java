package cc.mcwarzone.listener;

import cc.mcwarzone.OnlineGift_Server;
import cc.mcwarzone.gifts.GiftsUtils;
import cc.mcwarzone.message.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Messenger implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        String message = new String(bytes);
        if(message.startsWith("[OnlineGift]")){
            String data = message.substring(12,message.length());
            String[] temp = data.split("_");

            if(temp[0].equalsIgnoreCase("MODE1")){
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                List<String> commands = GiftsUtils.getMode1Commands(temp[1]);
                for(Player giftPlayer : players){
                    String ip = giftPlayer.getAddress().getAddress().toString();
                    if(OnlineGift_Server.ipSwitch && PlayerLoginListener.isSameIp(giftPlayer.getName(),ip)){
                        giftPlayer.sendMessage(MessageUtils.getMessage("SameIp"));
                        continue;
                    }
                    boolean isOp = giftPlayer.isOp();
                    giftPlayer.setOp(true);
                    for(String command : commands){
                        player.performCommand(command.replace("<playerName>",giftPlayer.getName()));
                    }
                    giftPlayer.setOp(isOp);
                    giftPlayer.sendMessage(MessageUtils.getMessage("Prefix") + MessageUtils.getMessage("PlayerGetGift"));
                }
            }else if(temp[0].equalsIgnoreCase("MODE2")){
                String playerString = temp[1];
                List<String> playerNames = new ArrayList<>();
                for(String playerName : playerString.split(",")){
                    playerNames.add(playerName);
                }
                for(String playerNameTemp : playerNames){
                    Player giftPlayer = Bukkit.getPlayer(playerNameTemp);
                    if(giftPlayer.isOnline()){
                        String ip = giftPlayer.getAddress().getAddress().toString();
                        if(OnlineGift_Server.ipSwitch && PlayerLoginListener.isSameIp(giftPlayer.getName(),ip)){
                            giftPlayer.sendMessage(MessageUtils.getMessage("SameIp"));
                            continue;
                        }
                        boolean isOp = giftPlayer.isOp();
                        giftPlayer.setOp(true);
                        HashMap map = GiftsUtils.getMode2RandomHashMap(giftPlayer);
                        List<String> commands = (List<String>) map.get("commands");
                        String playerMessage = (String) map.get("playerMessage");
                        String serverMessage = (String) map.get("serverMessage");
                        boolean isServerSay = (boolean) map.get("isServerSay");
                        for(String command : commands){
                            giftPlayer.performCommand(command.replace("<playerName>",giftPlayer.getName()));
                        }
                        if(isServerSay){
                            Bukkit.getServer().broadcastMessage(MessageUtils.getMessage("Prefix") + serverMessage.replace("<playerName>",giftPlayer.getName()));
                        }
                        giftPlayer.setOp(isOp);
                        giftPlayer.sendMessage(MessageUtils.getMessage("Prefix") + playerMessage);
                    }
                }
            }
        }
    }
}
