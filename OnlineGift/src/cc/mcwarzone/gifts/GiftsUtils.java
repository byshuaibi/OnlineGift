package cc.mcwarzone.gifts;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GiftsUtils {
    public static final HashMap<String, List<String>> MODE1COMMANDSMAP = new HashMap<>();

    //MODE2
    public static HashMap<String,HashMap> MODE2HASHMAP = new HashMap<>();


    public static void putCommandsToMap(String path,List<String> commands){
        MODE1COMMANDSMAP.put(path,commands);
    }
    public static void putMODE2Map(String path , HashMap map){
        MODE2HASHMAP.put(path,map);
    }
    public static List<String> getMode1Commands(String path){
        return MODE1COMMANDSMAP.get(path);
    }
    public static HashMap getMode2RandomHashMap(Player player){
        int randomInt = new Random().nextInt(101);
        for(String path : MODE2HASHMAP.keySet()){
            if(randomInt >= (int)MODE2HASHMAP.get(path).get("chance")){
                return MODE2HASHMAP.get(path);
            }
        }
        Iterator<String> it = MODE2HASHMAP.keySet().iterator();
        String endString = "";
        while (it.hasNext()){
            endString = it.next();
        }
        return MODE2HASHMAP.get(endString);
    }
    public static void remakeMODE2Map(){
        int tempChance = 0;
        String tempString = "";
        boolean first = true;
        for(String path : MODE2HASHMAP.keySet()){
            if(first){
                first = false;
                tempChance = (int)MODE2HASHMAP.get(path).get("chance");
                tempString = path;
                continue;
            }
            if(tempChance < (int)MODE2HASHMAP.get(path).get("chance")){
                tempString = path + "," + tempString;
            }else{
                tempString += "," + path;
            }
        }
        String[] temp = tempString.split(",");
        HashMap<String,HashMap> newMap = new HashMap<>();
        for(String path : temp){
            newMap.put(path,MODE2HASHMAP.get(path));
        }
        MODE2HASHMAP = newMap;
    }
}
