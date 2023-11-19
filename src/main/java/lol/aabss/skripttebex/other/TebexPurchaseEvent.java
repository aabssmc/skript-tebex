package lol.aabss.skripttebex.other;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TebexPurchaseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private String pack;


    public TebexPurchaseEvent(Player player, String pack){
        this.player = player;
        this.pack = pack;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPackage() {
        return pack;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
