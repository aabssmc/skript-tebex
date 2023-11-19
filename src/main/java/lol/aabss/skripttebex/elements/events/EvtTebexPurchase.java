package lol.aabss.skripttebex.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import lol.aabss.skripttebex.other.TebexPurchaseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("On Tebex Purchase")
@Description("Called when a purchase happens")
@Examples({
        "on tebex purchase:",
        "\tbroadcast \"%player% bought %event-string%\""
})
@Since("1.0")

public class EvtTebexPurchase extends SkriptEvent {

    static{
        Skript.registerEvent("On Tebex Purchese Event", EvtTebexPurchase.class, TebexPurchaseEvent.class,
                "[on] (tebex|buycraft[x]) purchase"
        );
        EventValues.registerEventValue(TebexPurchaseEvent.class, Player.class, new Getter<Player, TebexPurchaseEvent>(){
            @Override
            public Player get(TebexPurchaseEvent e) {
                return e.getPlayer();
            }
        },0);
        EventValues.registerEventValue(TebexPurchaseEvent.class, String.class, new Getter<String, TebexPurchaseEvent>(){
            @Override
            public String get(TebexPurchaseEvent e) {
                return e.getPackage();
            }
        },0);
    }

    @Override
    public boolean init(Literal<?> @NotNull [] args, int matchedPattern, SkriptParser.@NotNull ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        return true;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "tebex purchase";
    }
}
