package lol.aabss.skripttebex.other;

import com.google.gson.JsonObject;
import lol.aabss.skripttebex.SkriptTebex;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class TebexPurchase extends JavaPlugin {

    public JsonObject sales;

    @Override
    public void onEnable() {
        int mins = (int) SkriptTebex.getPlugin(SkriptTebex.class).getConfig().get("tebex-listener");
        int ticks = mins*60*20;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                JsonObject sale;
                try {
                    sale = TebexAPI.api("sales", "GET");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (sale != sales){
                    Bukkit.getServer().getPluginManager().callEvent(new TebexPurchaseEvent());
                }
                sales = sale;
            }
        }, 0, ticks);
    }

}
