package marcodev.deathswapultimate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class EventListener implements Listener {
    private final DeathSwapUltimate plugin;
    private final FileConfiguration config;

    public EventListener(FileConfiguration config, DeathSwapUltimate plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        event.setJoinMessage("");
        if (plugin.joinexecutioncheck == 1) {
            if (plugin.lobby != null) {
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(plugin.lobby);
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "luckperms user " + player.getName() + " parent set " + plugin.spect_lpgroup);
            }
        } else {
            if (plugin.defaultgamemode.equalsIgnoreCase("ADVENTURE")) {
                player.setGameMode(GameMode.ADVENTURE);
            } else if (plugin.defaultgamemode.equalsIgnoreCase("SURVIVAL")) {
                player.setGameMode(GameMode.SURVIVAL);
            } else if (plugin.defaultgamemode.equalsIgnoreCase("SPECTATOR")) {
                player.setGameMode(GameMode.SPECTATOR);
                plugin.getLogger().info("\u001B[31mPlayers are entering spectator mode");
            } else if (plugin.defaultgamemode.equalsIgnoreCase("CREATIVE")) {
                player.setGameMode(GameMode.CREATIVE);
                plugin.getLogger().info("\u001B[31mPlayers are entering creative mode");
            } else {
                plugin.getLogger().warning("\u001B[31mThe input mode specified in the config is invalid! Edit it in the config.yml.");
            }

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "luckperms user " + player.getName() + " parent set " + plugin.default_lpgroup);

            if (plugin.lobby != null) {
                player.teleport(plugin.lobby);
            }

        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player deathPlayer = event.getPlayer();

        if (plugin.lobby != null) {
            deathPlayer.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_DEATH.toString());
            deathPlayer.sendMessage(Lang.PREFIX.toString() + Lang.DEATH_USE_COMMAND1.toString() + " " + Lang.DEATH_USE_COMMAND2.toString() + " " + Lang.DEATH_USE_COMMAND3.toString());
            event.setRespawnLocation(plugin.lobby);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deathPlayer = event.getPlayer();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "luckperms user " + deathPlayer.getName() + " parent set " + plugin.spect_lpgroup);
        deathPlayer.setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

}