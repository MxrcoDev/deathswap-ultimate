package marcodev.deathswapultimate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {

    // dichiarazioni
    private final FileConfiguration config;
    private final DeathSwapUltimate instance;

    public TeleportCommand(FileConfiguration config, DeathSwapUltimate instance) {
        this.config = config;
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1 && player.hasPermission(instance.TELEPORT_PERMISSION)) {

                Player playertarget;

                playertarget = Bukkit.getPlayer(args[0]);

                if (playertarget == null) {
                    sender.sendMessage(Lang.PREFIX.toString() + Lang.TELEPORT_PLAYER_NOT_FOUND);
                    return true;
                } else if (playertarget.getName().equals(sender.getName())) {
                    sender.sendMessage(Lang.PREFIX.toString() + Lang.TELEPORT_PLAYER_INVALID);
                    return true;
                }
                ((Player) sender).teleport(playertarget.getLocation());
                player.sendMessage(Lang.PREFIX.toString() + Lang.TELEPORT_COMMAND + playertarget.getName());
                return true;
            }
        }

        return true;
    }

}
