package lol.aabss.skripttebex.other;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lol.aabss.skripttebex.SkriptTebex;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class TebexPurchase extends JavaPlugin {

    public JsonObject sales;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onEnable() {
        int mins = (int) SkriptTebex.getPlugin(SkriptTebex.class).getConfig().get("tebex-listener");
        int ticks = mins*60*20;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                JsonObject sale;
                try {
                    sale = TebexAPI.api("payments", "GET");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (sale != sales){
                    JsonObject newpurchase = sale.getAsJsonObject();
                    String user = newpurchase
                            .getAsJsonObject("player")
                            .getAsJsonPrimitive("name")
                            .getAsString();
                    JsonArray packages = sale.getAsJsonArray("packages");
                    JsonObject pack = packages.get(0).getAsJsonObject();
                    String name = pack.getAsJsonPrimitive("name").getAsString();
                    Bukkit.getServer().getPluginManager().callEvent(new TebexPurchaseEvent(Bukkit.getPlayer(user), name));
                }
                sales = sale;
            }
        }, 0, ticks);
    }

}
