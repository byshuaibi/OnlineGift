package cc.mcwarzone.task;

import cc.mcwarzone.OnlineGift_BC;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class GiftTask implements Runnable{
    int time;
    int tempTime = 0;
    public GiftTask(int time){
        this.time = time;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tempTime = tempTime + 1;
            if(tempTime >= time * 60){
                if(OnlineGift_BC.mode1){
                    //Mode 1
                    int onlineNumber = 0;
                    List<ServerInfo> servers = new ArrayList<>();
                    for(String serverName : BungeeCord.getInstance().getServers().keySet()){
                        if(OnlineGift_BC.blockList.contains(serverName)){
                            continue;
                        }else {
                            servers.add(BungeeCord.getInstance().getServers().get(serverName));
                            onlineNumber += BungeeCord.getInstance().getServers().get(serverName).getPlayers().size();
                        }
                    }
                    String giftName = getMaxGiftMode1(onlineNumber);
                    if(giftName != null && !giftName.equalsIgnoreCase("NONONO")){
                        for(ServerInfo server : servers){
                            String data = "[OnlineGift]MODE1_" + giftName;
                            server.sendData("CYOG",data.getBytes());
                        }
                        OnlineGift_BC.runTaskTimer();
                        return;
                    }else {
                        OnlineGift_BC.runTaskTimer();
                        return;
                    }
                }else {
                    //Mode 2
                    int onlineNumber = 0;
                    List<ServerInfo> servers = new ArrayList<>();
                    for(String serverName : BungeeCord.getInstance().getServers().keySet()){
                        if(OnlineGift_BC.blockList.contains(serverName)){
                            continue;
                        }
                        servers.add(BungeeCord.getInstance().getServers().get(serverName));
                        onlineNumber += BungeeCord.getInstance().getServers().get(serverName).getPlayers().size();
                    }
                    int number = onlineNumber * (OnlineGift_BC.pick2Pitch / 100);
                    if(number < OnlineGift_BC.min){
                        number = OnlineGift_BC.min;
                    }
                    List<ProxiedPlayer> players = new ArrayList<>();
                    String playerString = "";
                    for(ServerInfo server : servers){
                        players.addAll(server.getPlayers());
                    }
                    for(int i = 0 ; i < number ; i++){
                        int tempInt = new Random().nextInt(players.size());
                        playerString += players.get(tempInt).getName() + ",";
                    }
                    for(ServerInfo server : servers){
                        String data = "[OnlineGift]MODE2_" + playerString;
                        server.sendData("CYOG",data.getBytes());
                    }
                    OnlineGift_BC.runTaskTimer();
                    return;
                }
            }
        }
    }
    public static String getMaxGiftMode1(int number){
        int temp = 0;
        String minString = OnlineGift_BC.pick1Level.get(0);
        int min = Integer.parseInt(minString.split(",")[0]);
        HashMap<Integer,String> hashMap = new HashMap<>();
        if(number >= min){
            temp = min;
            for(String str : OnlineGift_BC.pick1Level){
                int strNum = Integer.parseInt(str.split(",")[0]);
                hashMap.put(strNum,str.split(",")[1]);
                if(strNum > min && number >= strNum){
                    temp = strNum;
                }
            }
        }
        if(temp == 0){
            return "NONONO";
        }else{
            return hashMap.get(temp);
        }
    }
}
