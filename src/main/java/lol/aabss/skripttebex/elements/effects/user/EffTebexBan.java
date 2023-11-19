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

public class EffTebexBan extends Effect {

    static {
        Skript.registerEffect(EffTebexBan.class,
                "(create|make) [a [new]] tebex ban for %string% [with reason %-string%]",
                "(create|make) [a [new]] tebex ip[(-| )]ban for %string% [with reason %-string%]"
        );
    }

    boolean isIP;
    Expression<String> reason;
    Expression<String> banner;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        isIP = (matchedPattern == 1);
        banner = (Expression<String>) exprs[0];
        reason = (Expression<String>) exprs[1];
        return true;
    }

    @Override
    protected void execute(@NotNull Event e) {
        try {
            JsonObject body;
            String r = null;
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

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "create tebex ban";
    }
}
