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

@Name("Server ID")
@Description("Gets the tebex server id")
@Examples({
        "send tebex server id"
})
@Since("1.0")

public class ExprServerID extends SimpleExpression<Integer> {

    static{
        Skript.registerExpression(
                ExprServerID.class, Integer.class, ExpressionType.SIMPLE,
                "[the] (tebex|buycraft[x]) server id[entification] [number]"
        );
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected Integer [] get(Event e) {
        try {
            JsonObject api = TebexAPI.api("information", "GET");
            if (api == null){
                Skript.error("Invalid Tebex Secret. Edit in config.yml");
                return null;
            }
            int a = api
                    .getAsJsonObject("server")
                    .getAsJsonPrimitive("id")
                    .getAsInt();
            return new Integer[]{a};
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "tebex server id";
    }

    @Override
    public boolean init(Expression<?> [] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
