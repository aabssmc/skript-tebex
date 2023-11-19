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

@Name("Server Name")
@Description("Gets the tebex server name")
@Examples({
        "send tebex server name"
})
@Since("1.0")

public class ExprServerName extends SimpleExpression<String> {

    static{
        Skript.registerExpression(
                ExprServerName.class, String.class, ExpressionType.SIMPLE,
                "[the] (tebex|buycraft[x]) server name"
        );
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected String [] get(Event e) {
        try {
            JsonObject api = TebexAPI.api("information", "GET");
            if (api == null){
                Skript.error("Invalid Tebex Secret. Edit in config.yml");
                return null;
            }
            String a = api
                    .getAsJsonObject("server")
                    .getAsJsonPrimitive("name")
                    .getAsString();
            return new String[]{a};
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "tebex server name";
    }

    @Override
    public boolean init(Expression<?> [] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
