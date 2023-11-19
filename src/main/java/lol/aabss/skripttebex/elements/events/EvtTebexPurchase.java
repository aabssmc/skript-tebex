package lol.aabss.skripttebex.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import lol.aabss.skripttebex.other.TebexPurchaseEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("On Tebex Purchase")
@Description("Called when a purchase happens")
@Examples({
        "#will add event values later",
        "on tebex purchase:",
        "\tbroadcast \"someone bought something\""
})
@Since("1.0")

public class EvtTebexPurchase extends SkriptEvent {

    static{
        Skript.registerEvent("On Tebex Purchese Event", EvtTebexPurchase.class, TebexPurchaseEvent.class,
                "[on] tebex purchase"
        );
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
