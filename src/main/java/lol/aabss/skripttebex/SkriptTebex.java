package lol.aabss.skripttebex;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import net.md_5.bungee.api.ChatColor;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class SkriptTebex extends JavaPlugin implements CommandExecutor {

    private static SkriptTebex instance;
    private SkriptAddon addon;

    public static boolean secretvalid;

    @Override
    public void onEnable() {
        getCommand("skript-tebex").setExecutor(this);
        Metrics metrics = new Metrics(this, 20162);
        instance = this;
        try {
            addon = Skript.registerAddon(this)
                    .loadClasses("lol.aabss.skripttebex", "elements");
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveDefaultConfig();
        getLogger().info("skript-tebex has been enabled!");
    }

    public static SkriptTebex getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            sender.sendMessage(ChatColor.RED + "/skript-tebex <reload|version>");
        }
        else if (args[0].equals("reload") || args[0].equals("r")){
            if (sender.hasPermission("skripttebex.reload")){
                reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Reload successful!");
                String secret = (String) SkriptTebex.getPlugin(SkriptTebex.class).getConfig().get("tebex-secret");
                try {
                    if (!TebexAPI.isSecretValid(secret)){
                        sender.sendMessage(ChatColor.RED + "Invalid Tebex secret");
                        secretvalid = false;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                sender.sendMessage(ChatColor.RED + "No permission.");
            }
        }
        else if (args[0].equals("version") || args[0].equals("ver")){
            reloadConfig();
            sender.sendMessage(ChatColor.YELLOW + "This server is running skript-tebex v" + this.getDescription().getVersion() + "!");
        }
        else{
            sender.sendMessage(ChatColor.RED + "/skript-tebex <reload|version>");
        }
        return super.onCommand(sender, command, label, args);
    }
}
