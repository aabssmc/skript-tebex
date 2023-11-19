package lol.aabss.skripttebex.other;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TebexPurchaseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public TebexPurchaseEvent(){
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
