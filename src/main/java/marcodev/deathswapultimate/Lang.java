package marcodev.deathswapultimate;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
    PREFIX("prefix", "&7&l[&4&lDeath&c&lSwap&7&l] &7&l>&f&l> "),
    INCORRET_USAGE("incorrect-usage", "&c&lIncorrect usage! \n&7Use /deathswap <setlobby>/<start>"),
    ALREADY_IN_USE("already-in-use", "&bCommand already in use!"),
    SETLOBBY("setlobby-command", "&bLobby successfully configured"),
    FORCE_END("forced-end-kick-message", "&cThe match is over"),
    MATCH_IS_STARTING("match-is-starting-start", "Match is starting in:"),
    MATCH_STARING_MESSAGE("starting-message", "Game started!"),
    SWAP_COOLDOWN_MESSAGE("swap-cooldown-message", "&cSwap in"),
    SECONDS("seconds", "&cseconds"),
    SECOND("second", "&csecond"),
    SWAP("swap", "&a&lSwap!"),
    END_TITLE("end-title", "Game ended!"),
    WINNER_COLOR("winner-color", "&b&l"),
    WINNER_MESSAGE("winner-message", "&awon the game!"),
    PLAYER_DEATH("player-death-message", "&b&lYou're dead!"),
    DEATH_USE_COMMAND1("use-teleport-command-1", "&bUse"),
    DEATH_USE_COMMAND2("use-teleport-command-2", "&a/teleport <player name>"),
    DEATH_USE_COMMAND3("use-teleport-command-3", "&bto spectate to other players."),
    TELEPORT_PLAYER_NOT_FOUND("teleport-command-player-not-found", "&cPlayer not found!"),
    TELEPORT_PLAYER_INVALID("teleport-command-player-invalid", "&cYou cannot spectate to yourself"),
    TELEPORT_COMMAND("teleport-command-used", "&bYou are now specting &a");


    static YamlConfiguration LANG;
    private String path;
    private String def;

    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(YamlConfiguration config) { LANG = config;}

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    public String getDefault() {
        return this.def;
    }
    public String getPath() {
        return this.path;
    }
}
