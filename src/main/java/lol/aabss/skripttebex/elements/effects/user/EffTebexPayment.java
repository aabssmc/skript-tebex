package lol.aabss.skripttebex.elements.effects.user;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import lol.aabss.skripttebex.TebexAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;

public class EffTebexPayment extends Effect {

    static{
        Skript.registerEffect(EffTebexPayment.class,
                "(create|make) [a [new]] tebex payment (from|with) [package id] %number% with options %string% with price %integer% as player %string% [with note %-string%]"
        );
    }

    Expression<Number> packageid;
    Expression<String> options;
    Expression<Integer> price;
    Expression<String> player;
    Expression<String> note;

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        packageid = (Expression<Number>) exprs[0];
        options = (Expression<String>) exprs[1];
        price = (Expression<Integer>) exprs[2];
        player = (Expression<String>) exprs[3];
        note = (Expression<String>) exprs[4];
        return true;
    }

    @Override
    protected void execute(@NotNull Event e) {
        try {
            String n = null;
            if (note.getSingle(e) == null) {
                n = "Skript payment";
            }
            else{
                n = note.getSingle(e);
            }
            JsonObject body = new JsonObject();
            body.addProperty("note", n);
            body.addProperty("options", options.getSingle(e));
            body.addProperty("price", price.getSingle(e));
            body.addProperty("player", player.getSingle(e));
            body.addProperty("id", packageid.getSingle(e));
            TebexAPI.api("payments", "POST", body);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "create tebex payment";
    }
}
