package cc.mcwarzone;

import cc.mcwarzone.task.GiftTask;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class OnlineGift_BC extends Plugin {
    public static String root;
    public static String timeString;
    public static boolean mode1;
    public static List<String> blockList;
    public static List<String> pick1Level;
    public static int min;
    public static int pick2Pitch;
    @Override
    public void onEnable() {
        this.getProxy().registerChannel("CYOG");
        root = this.getDataFolder().getAbsolutePath();

        try{
            ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(new File(root, "config.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
        this.getProxy().getLogger().info("读取Config.yml.");
        update();
        this.getProxy().getLogger().info("在线奖励插件运行.");
    }
    public static void runTaskTimer(){
        int time = new Random().nextInt(Integer.parseInt(timeString.split(",")[1]) + 1) + Integer.parseInt(timeString.split(",")[0]);
        GiftTask task = new GiftTask(time);
        Thread thread = new Thread(task);
        thread.start();
    }
    public static void update(){
        File file = new File(root,"config.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        timeString = config.getString("PickTime");
        if(config.getInt("Mode") == 1){
            mode1 = true;
        }else {
            mode1 = false;
        }
        blockList = config.getStringList("BlockedServerName");
        pick1Level = config.getStringList("Pick1Level");
        pick2Pitch = config.getInt("Pick2Pitch");
        min = config.getInt("Min");
        runTaskTimer();
    }
}
