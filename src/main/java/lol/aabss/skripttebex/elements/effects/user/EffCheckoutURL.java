package lol.aabss.skripttebex.elements.effects.user;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.*;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import lol.aabss.skripttebex.other.TebexAPI;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;

import static lol.aabss.skripttebex.SkriptTebex.secretvalid;

@Name("Checkout URL")
@Description("Creates a new checkout url for a package as a player")
@Examples({
        "make a tebex checkout url with package id \"123\" as \"aabss\" and store it in {_url}",
        "send \"<link:{_url}>Click here to go to checkout!<reset>\" to player"
})
@Since("1.0")

public class EffCheckoutURL extends Effect {

    static{
        Skript.registerEffect(EffCheckoutURL.class,
                "(create|make) [a [new]] [tebex] checkout url with package [id] %string% as [player] %string% and store it in %object%"
        );
    }

    Expression<String> id;
    Expression<String> player;
    Variable<?> var;

    @Override
    protected void execute(@NotNull Event e) {
        if (secretvalid){
            try {
                JsonObject body = new JsonObject();
                body.addProperty("package_id", id.getSingle(e));
                body.addProperty("username", player.getSingle(e));
                JsonObject api = TebexAPI.api("bans", "POST", body);
                assert api != null;
                String a = api
                        .getAsJsonPrimitive("url")
                        .getAsString();
                Object[] change = new Object[] {a};
                if (var != null) {
                    var.change(e, change, Changer.ChangeMode.SET);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else{
            Skript.error("Invalid Tebex Secret. Edit in config.yml");
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "checkout url";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        player = (Expression<String>) exprs[1];
        if (exprs[2] instanceof Variable<?>){
            var = (Variable<?>) exprs[2];
        }
        else{
            Skript.error("Object must be a variable");
            return false;
        }
        return true;
    }
}
