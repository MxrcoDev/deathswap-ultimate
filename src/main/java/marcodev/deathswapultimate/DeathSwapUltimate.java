package marcodev.deathswapultimate;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class DeathSwapUltimate extends JavaPlugin {

    // dichiarazioni
    public static final String ADMIN_PERMISSION = "deathswap.admin";
    public static final String TELEPORT_PERMISSION = "deathswap.teleport";
    public int joinexecutioncheck;
    public int swapexecutioncheck;
    public int startexecutioncheck;
    public int swapendcheck;
    public int startcooldown;
    public int swapcooldown;
    public BukkitTask startTask;
    public BukkitTask swapTask;
    public Location lobby;
    public String defaultgamemode;
    public String default_lpgroup;
    public String spect_lpgroup;

    // instanza
    public static DeathSwapUltimate instance;
    public static DeathSwapUltimate plugin;





    @Override
    public void onEnable() {

        instance = this;
        plugin = this;

        getLogger().info("\u001B[31m-----------------------------------------------");
        getLogger().info(" ");
        getLogger().info("\u001B[31m                  DeathSwap v1.0");
        getLogger().info("\u001B[31m              Developed by MxrcoDev_!");
        getLogger().info(" ");
        getLogger().info("\u001B[32m                  Plugin abilitato!");
        getLogger().info(" ");
        getLogger().info("\u001B[31m------------------------------------------------");

        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        startcooldown = config.getInt("start-cooldown");
        swapcooldown = config.getInt("swap-cooldown");
        defaultgamemode = config.getString("default-game-mode");
        startexecutioncheck = 0;
        swapexecutioncheck = 0;
        joinexecutioncheck = 0;
        default_lpgroup = config.getString("default-luckperms-group");
        spect_lpgroup = config.getString("spectator-luckperms-group");

        loadLang();

        getServer().getPluginManager().registerEvents(new EventListener(config, plugin), this);
        this.getCommand("deathswap").setExecutor(new CustomCommandExecutor(config, instance));
        this.getCommand("teleport").setExecutor(new TeleportCommand(config, instance));


    }

    public void loadLang() {
        File lang = new File(getDataFolder(), "lang.yml");
        YamlConfiguration langConfig = YamlConfiguration.loadConfiguration(lang);
        if (!lang.exists()) {
            try {
                langConfig.save(lang);
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().severe("Could not create language file!");
                getLogger().severe("Disabling Plugin!");
                getServer().getPluginManager().disablePlugin(this);
            }
        }
        for (Lang item : Lang.values()) {
            if (langConfig.getString(item.getPath()) == null) {
                langConfig.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(langConfig);
        try {
            langConfig.save(lang);
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Could not edit language file!");
            getLogger().severe("Disabling Plugin!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

}
