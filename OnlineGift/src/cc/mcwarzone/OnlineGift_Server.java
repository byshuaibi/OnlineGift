package cc.mcwarzone;

import cc.mcwarzone.command.GiftsCommands;
import cc.mcwarzone.gifts.GiftsUtils;
import cc.mcwarzone.listener.Messenger;
import cc.mcwarzone.listener.PlayerLoginListener;
import cc.mcwarzone.message.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OnlineGift_Server extends JavaPlugin {
    public static String root;
    public static boolean ipSwitch;
    @Override
    public void onEnable() {
        root = this.getDataFolder().getAbsolutePath();
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(),this);
        update();
        this.getCommand("cyog").setExecutor(new GiftsCommands());
        this.getServer().getMessenger().registerIncomingPluginChannel(this,"CYOG",new Messenger());
        this.getLogger().info("Plugin is enable.");
    }
    public static void update(){
        File file = new File(root,"config.yml");
        FileConfiguration config = load(file);

        for(String path : config.getConfigurationSection("Messages").getKeys(false)){
            MessageUtils.putMessageMap(path,config.getString("Messages." + path).replace("&","§"));
        }
        for(String path : config.getConfigurationSection("Pick1Gift").getKeys(false)){
            GiftsUtils.putCommandsToMap(path,config.getStringList("Pick1Gift." + path + ".commands"));
        }
        for(String path : config.getConfigurationSection("Pick2Gift").getKeys(false)){
            List<String> commands = config.getStringList("Pick2Gift." + path + ".commands");
            String playerMessage = config.getString("Pick2Gift." + path + ".message.player").replace("&","§");
            String serverMessage = config.getString("Pick2Gift." + path + ".message.server").replace("&","§");
            int chance = config.getInt("Pick2Gift." + path + ".chance");
            boolean isServerSay = config.getBoolean("Pick2Gift." + path + ".isServerSay");
            HashMap hashMap = new HashMap();
            hashMap.put("commands",commands);
            hashMap.put("playerMessage",playerMessage);
            hashMap.put("serverMessage",serverMessage);
            hashMap.put("chance",chance);
            hashMap.put("isServerSay",isServerSay);
            GiftsUtils.putMODE2Map(path,hashMap);
        }
        GiftsUtils.remakeMODE2Map();
        ipSwitch = config.getBoolean("ProtectSameIp");
    }
    public static FileConfiguration load(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }
}
