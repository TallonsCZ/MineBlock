package me.tallonscze.mineblock;

import me.tallonscze.mineblock.commands.CreateIsland;
import me.tallonscze.mineblock.commands.OpenMainMenu;
import me.tallonscze.mineblock.commands.SetSpawn;
import me.tallonscze.mineblock.commands.Spawn;
import me.tallonscze.mineblock.event.IslandEvent;
import me.tallonscze.mineblock.event.PlayerEvent;
import me.tallonscze.mineblock.utility.ConfigUtility;
import me.tallonscze.mineblock.utility.OtherUtility;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Mineblock extends JavaPlugin {

    @Override
    public void onEnable() {

        int configVersion = 3;

            // Plugin startup logic
            getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
            getServer().getPluginManager().registerEvents(new IslandEvent(), this);

            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }

            File playerFile = new File(getDataFolder(), "players");

            if (!playerFile.exists()) {
                playerFile.mkdirs();
            }

            File schemFile = new File(getDataFolder(), "schematic");

            if (!schemFile.exists()) {
                schemFile.mkdirs();
            }

            File config = new File(getDataFolder(), "config.yml");
            if (!config.exists()) {
                saveResource("config.yml", false);
            } else if (ConfigUtility.getConfig().getInt("global.version") != configVersion) {
                config.delete();
                saveResource("config.yml", false);
            }
            Location spawnLocation = getServer().getWorld("world").getSpawnLocation();
            if (spawnLocation != null && ConfigUtility.getConfig().getBoolean("global.spawnset") != true) {
                OtherUtility.setSpawnLocation(spawnLocation);
                ConfigUtility.getConfig().set("global.spawnset", true);
            }


            getCommand("play").setExecutor(new CreateIsland());
            getCommand("spawn").setExecutor(new Spawn());
            getCommand("setspawn").setExecutor(new SetSpawn());
            getCommand("upgrade").setExecutor(new OpenMainMenu());
        }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
