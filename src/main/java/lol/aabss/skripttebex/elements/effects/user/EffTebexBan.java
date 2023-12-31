package lol.aabss.skripttebex.elements.effects.user;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import lol.aabss.skripttebex.other.TebexAPI;
import org.bukkit.event.Event;


import javax.annotation.Nullable;
import java.io.IOException;

import static lol.aabss.skripttebex.SkriptTebex.secretvalid;

@Name("Ban User")
@Description("Bans a user from the tebex store")
@Examples({
        "make a new tebex ban for \"Hackerboy420\" with reason \"Refunding payments\""
})
@Since("1.0")

public class EffTebexBan extends Effect {

    static {
        Skript.registerEffect(EffTebexBan.class,
                "(create|make) [a [new]] (tebex|buycraft[x]) ban for %string% [with reason %-string%]",
                "(create|make) [a [new]] (tebex|buycraft[x]) ip[(-| )]ban for %string% [with reason %-string%]"
        );
    }

    boolean isIP;
    Expression<String> reason;
    Expression<String> banner;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        isIP = (matchedPattern == 1);
        banner = (Expression<String>) exprs[0];
        reason = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (secretvalid){
            try {
                JsonObject body;
                String r;
                if (reason.getSingle(e) == null){
                    r = "Banned with no reason.";
                }
                else{
                    r = reason.getSingle(e);
                }
                if (isIP){
                    body = new JsonObject();
                    body.addProperty("reason", r);
                    body.addProperty("ip", banner.getSingle(e));
                }
                else{
                    body = new JsonObject();
                    body.addProperty("reason", r);
                    body.addProperty("user", banner.getSingle(e));
                }
                TebexAPI.api("bans", "POST", body);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else{
            Skript.error("Invalid Tebex Secret. Edit in config.yml");
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "create tebex ban";
    }
}
