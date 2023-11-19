package lol.aabss.skripttebex.elements.expressions.info;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import lol.aabss.skripttebex.other.TebexAPI;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import java.io.IOException;

@Name("Online Mode")
@Description("Gets the online mode of tebex")
@Examples({
        "send tebex online mode"
})
@Since("1.0")

public class ExprOnlineMode extends SimpleExpression<Boolean> {

    static{
        Skript.registerExpression(
                ExprOnlineMode.class, Boolean.class, ExpressionType.SIMPLE,
                "[the] (tebex|buycraft[x]) online mode [status]"
        );
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected Boolean [] get(Event e) {
        try {
            JsonObject api = TebexAPI.api("information", "GET");
            if (api == null){
                Skript.error("Invalid Tebex Secret. Edit in config.yml");
                return null;
            }
            boolean a = api
                    .getAsJsonObject("account")
                    .getAsJsonPrimitive("online_mode")
                    .getAsBoolean();
            return new Boolean[]{a};
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "tebex online mode";
    }

    @Override
    public boolean init(Expression<?> [] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
