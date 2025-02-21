package marcodev.deathswapultimate;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.logging.Logger;

public class CustomCommandExecutor implements CommandExecutor, TabCompleter {

    // system
    private static final String[] POSSIBLE_VALUES = {"start", "setlobby", "end"};

    // Arrays
    public List<UUID> UUIDS = new ArrayList<>();
    public List X = new ArrayList<>();
    public List Y = new ArrayList<>();
    public List Z = new ArrayList<>();
    public List WINNER = new ArrayList<>();

    // dichiarazioni
    private final FileConfiguration config;
    private final DeathSwapUltimate instance;

    public CustomCommandExecutor(FileConfiguration config, DeathSwapUltimate instance) {
        this.config = config;
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(Lang.PREFIX.toString() + Lang.INCORRET_USAGE.toString());
        } else if (args.length == 1 && sender.hasPermission(instance.ADMIN_PERMISSION)){

            instance.swapendcheck = 0;

            if (args[0].equals(POSSIBLE_VALUES[0])) {

                if(instance.startexecutioncheck == 1) {
                    sender.sendMessage(Lang.PREFIX.toString() + Lang.ALREADY_IN_USE.toString());
                } else if(instance.startexecutioncheck == 0) {

                    // toggle execution check
                    DeathSwapUltimate.instance.startexecutioncheck = 1;
                    DeathSwapUltimate.instance.joinexecutioncheck = 1;

                    // toggle startTask
                    DeathSwapUltimate.instance.startTask = DeathSwapUltimate.instance.getServer().getScheduler().runTaskTimer((Plugin) instance, new Runnable() {
                        @Override
                        public void run() {
                            if (DeathSwapUltimate.instance.startcooldown > 0) {
                                for(Player title : Bukkit.getOnlinePlayers()) {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"" + instance.startcooldown + "\", \"bold\":true, \"italic\":true, \"color\":\"red\"}");
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle " + "{\"text\":\"" + Lang.MATCH_IS_STARTING.toString() + "\", \"color\":\"yellow\"}");
                                }
                                DeathSwapUltimate.instance.startcooldown--;
                            } else if (DeathSwapUltimate.instance.startcooldown == 0){
                                instance.joinexecutioncheck = 1;
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "minecraft:gamemode survival @a");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "deathswap randomtp");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "effect give @a minecraft:saturation 60 10");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "effect give @a minecraft:regeneration 60 10");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title @a subtitle " + "{\"text\":\"\"}");
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"" + Lang.MATCH_STARING_MESSAGE.toString() + "\", \"bold\":true, \"italic\":true, \"color\":\"green\"}");

                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    Random random = new Random();
                                    int x = random.nextInt(1500*2) - 1500;
                                    int z = random.nextInt(1500*2) - 1500;
                                    int y = instance.getServer().getWorld("world").getHighestBlockYAt(x, z) + 1;

                                    player.teleport(new Location(player.getWorld(), x, y, z));
                                }


                                DeathSwapUltimate.instance.swapTask = DeathSwapUltimate.instance.getServer().getScheduler().runTaskTimer((Plugin) instance, new Runnable() {
                                    @Override
                                    public void run() {

                                        if (instance.swapexecutioncheck == 0) {
                                            for (Player plist : Bukkit.getOnlinePlayers()) {
                                                if(plist.getGameMode().equals(GameMode.SURVIVAL)) {
                                                    UUIDS.add(plist.getUniqueId());
                                                }
                                            }

                                            if (UUIDS.size() == 1) {
                                                for (Player winner : Bukkit.getOnlinePlayers()) {
                                                    if (winner.getGameMode().equals(GameMode.SURVIVAL)) {
                                                        WINNER.add(winner.getName());
                                                    }
                                                }


                                                for (Player player : Bukkit.getOnlinePlayers()) {
                                                    player.sendMessage(Lang.PREFIX.toString() + Lang.WINNER_COLOR.toString() + WINNER.get(0) + " " + Lang.WINNER_MESSAGE.toString());
                                                }
                                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title @a title {\"text\":\"" + Lang.END_TITLE.toString() + "\", \"bold\":true, \"italic\":true, \"color\":\"green\"}");
                                                UUIDS.clear();
                                                WINNER.clear();
                                                instance.swapTask.cancel();



                                            } else {
                                                UUIDS.clear();
                                            }
                                        }

                                        if (instance.swapcooldown > 10) {

                                            instance.swapcooldown--;

                                        } else if (instance.swapcooldown <= 10 && instance.swapcooldown > 1) {

                                            for (Player messaggio : Bukkit.getOnlinePlayers()) {
                                                messaggio.sendMessage(Lang.SWAP_COOLDOWN_MESSAGE.toString() + " " + instance.swapcooldown + " " + Lang.SECONDS.toString());
                                            }
                                            instance.swapcooldown--;

                                        } else if (instance.swapcooldown == 1) {
                                            for (Player messaggio : Bukkit.getOnlinePlayers()) {
                                                messaggio.sendMessage(Lang.SWAP_COOLDOWN_MESSAGE.toString() + " " + instance.swapcooldown + " " + Lang.SECOND.toString());
                                            }
                                            instance.swapcooldown--;
                                        } else {
                                            for (Player messaggio : Bukkit.getOnlinePlayers()) {
                                                messaggio.sendMessage(Lang.SWAP.toString());
                                            }


                                            for (Player plist : Bukkit.getOnlinePlayers()) {
                                                    if (plist.getGameMode().equals(GameMode.SURVIVAL)) {
                                                        UUIDS.add(plist.getUniqueId());

                                                        X.add(plist.getX());
                                                        Y.add(plist.getY());
                                                        Z.add(plist.getZ());
                                                    }
                                            }

                                            Collections.rotate(X, 1);
                                            Collections.rotate(Y, 1);
                                            Collections.rotate(Z, 1);

                                            int a = 0;
                                            if ( a == 0 ) {
                                                for (int i = 0; i < UUIDS.size(); i++) {
                                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "minecraft:tp " + UUIDS.get(i) + " " + X.get(i) + " " + Y.get(i) + " " + Z.get(i) + " ");
                                                }
                                            }

                                            instance.swapcooldown = config.getInt("swap-cooldown");

                                            UUIDS.clear();
                                            X.clear();
                                            Y.clear();
                                            Z.clear();
                                        }
                                    }
                                },0L ,20L);

                                // Reset startTask
                                DeathSwapUltimate.instance.joinexecutioncheck = 0;
                                DeathSwapUltimate.instance.startexecutioncheck = 0;
                                DeathSwapUltimate.instance.startTask.cancel();
                            }
                        }
                    }, 0L, 20L);

                    DeathSwapUltimate.instance.startcooldown = config.getInt("start-cooldown");
                }

            } else if (args[0].equals(POSSIBLE_VALUES[1])) {

                instance.lobby = ((Player) sender).getLocation();
                config.set("lobby.world", instance.lobby.getWorld().getName());
                config.set("lobby.x", instance.lobby.getX());
                config.set("lobby.y", instance.lobby.getY());
                config.set("lobby.z", instance.lobby.getZ());
                instance.saveConfig();
                sender.sendMessage(Lang.PREFIX.toString() + Lang.SETLOBBY.toString());

            } else if (args[0].equals(POSSIBLE_VALUES[2])) {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getGameMode().equals(GameMode.SURVIVAL) || player.getGameMode().equals(GameMode.SPECTATOR)) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "kick " + player.getName() + " " + Lang.FORCE_END.toString());
                    }
                }

            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> result = new ArrayList<>();

        if (args.length == 1) {
            for (String arg : POSSIBLE_VALUES) {
                if (arg.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(arg);
                }
            }
        }
        return result;
    }
}
