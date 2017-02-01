package uk.co.umbaska.modules.factions.skript.expressions;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;
import uk.co.umbaska.registration.UmbaskaPropertyExpression;
import uk.co.umbaska.registration.annotations.Name;
import uk.co.umbaska.registration.annotations.Syntaxes;

/**
 * @author Andrew Tran
 */
@Name("Name of Faction")
@Syntaxes({"[display] (name|title) of %umbfactions%", "%umbfactions%'[s] [display] (name|title)"})
public class ExprNameOfFaction extends UmbaskaPropertyExpression<Faction, String> {
    Expression<Faction> faction;
    @Override
    protected String[] get(Event event, Faction[] factions) {
        return get(factions, new Converter<Faction, String>() {
            @Override
            public String convert(Faction faction) {
                if (faction == null){
                    return null;
                }
                return faction.getName();
            }
        });
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        faction = (Expression<Faction>) expressions[0];
        setExpr(faction);
        return true;
    }
}
