package lol.aabss.skripttebex.elements.expressions.user;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.event.Event;
import lol.aabss.skripttebex.other.TebexAPI;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;

public class ExprTebexBanList extends SimpleExpression<String> {

    static{
        Skript.registerExpression(ExprTebexBanList.class, String.class, ExpressionType.SIMPLE,
                "[the] (tebex|buycraft[x]) ban[( |-)]list",
                "[all] [the] banned (tebex|buycraft[x]) users"
        );
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable String[] get(Event e) {
        try {
            ArrayList<String> list = new ArrayList<>();
            JsonObject api = TebexAPI.api("bans", "GET");
            if (api == null){
                Skript.error("Invalid Tebex Secret. Edit in config.yml");
                return null;
            }
            JsonArray dataArray = api.getAsJsonArray("data");
            for (JsonElement element : dataArray) {
                JsonObject userObject = element.getAsJsonObject().getAsJsonObject("user");
                if (userObject.has("ign")) {
                    String ignValue = userObject.getAsJsonPrimitive("ign").getAsString();
                    list.add(ignValue);
                }
            }
            return list.toArray(new String[0]);
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
        return "tebex ban list";
    }

    @Override
    public boolean init(Expression<?> [] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
