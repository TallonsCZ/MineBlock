package me.tallonscze.mineblock.utility;

import me.tallonscze.mineblock.Mineblock;
import me.tallonscze.mineblock.data.InventoryData;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventoryUtility {

    private static Map<String, InventoryData> inventories = new HashMap<>();

    public static void loadAllInventories(){
        File pathToInventory = new File(Mineblock.getPlugin(Mineblock.class).getDataFolder() + "/inventories");
        File[] allFiles = pathToInventory.listFiles();
        int numberOfFiles = allFiles.length;
        if(numberOfFiles == 0){
            return;
        }
        for(int i = 0; i != numberOfFiles; i++){
            InventoryData data = new InventoryData(allFiles[i].getName());

            setInventory(data, data.getId());
            int ji = i+1;
            System.out.println("[MineBlock] Loaded Inventory: " + ji + "/" +numberOfFiles);
        }
    }

    public static void setInventory(InventoryData data, String name){
        inventories.put(name, data);
    }

    public static Inventory getInventory(String name){
        if(!inventories.containsKey(name)){
            return null;
        }
        return inventories.get(name).getInventory();
    }

    public static InventoryData getInventoryData(Inventory inventory) {
        Optional<InventoryData> optionalInventoryData = inventories.values()
                .stream()
                .filter(value -> value.getInventory() == inventory)
                .findAny();

        return optionalInventoryData.orElse(null);
    }

    public static boolean containsInventory(Inventory inventory){
        return inventories.values().stream().anyMatch(value -> value.getInventory() == inventory);
    }
}
