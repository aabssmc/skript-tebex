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

@Name("Domain")
@Description("Gets the tebex domain/url")
@Examples({
        "send tebex domain # example.tebex.io"
})
@Since("1.0")

public class ExprDomain extends SimpleExpression<String> {

    static{
        Skript.registerExpression(
                ExprDomain.class, String.class, ExpressionType.SIMPLE,
                "[the] (tebex|buycraft[x]) (domain|url)"
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
                    .getAsJsonObject("account")
                    .getAsJsonPrimitive("domain")
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
        return "tebex domain";
    }

    @Override
    public boolean init(Expression<?> [] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
