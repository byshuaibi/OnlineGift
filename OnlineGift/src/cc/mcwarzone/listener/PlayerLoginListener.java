package cc.mcwarzone.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerLoginListener implements Listener {
    private static final HashMap<String,String> playerIpMap = new HashMap<>();

    @EventHandler
    public void join(PlayerJoinEvent event){
        String ip = event.getPlayer().getAddress().getAddress().toString();
        String playerName = event.getPlayer().getName();
        playerIpMap.put(playerName,ip);
    }

    public static boolean isSameIp(String playerName,String ip){
        for(String temp : playerIpMap.keySet()){
            if(!temp.equals(playerName) && playerIpMap.get(temp).equals(ip)){
                return true;
            }
        }
        return false;
    }

}
