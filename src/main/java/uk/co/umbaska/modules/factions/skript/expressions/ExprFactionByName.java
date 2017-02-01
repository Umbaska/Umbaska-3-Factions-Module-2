package uk.co.umbaska.modules.factions.skript.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.modules.factions.FactionsModule;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;
import uk.co.umbaska.registration.SimpleUmbaskaExpression;
import uk.co.umbaska.registration.annotations.Name;
import uk.co.umbaska.registration.annotations.Syntaxes;

/**
 * @author Andrew Tran
 */
@Name("Faction with Name")
@Syntaxes("[the] (faction|kingdom|guild) with name %string%")
public class ExprFactionByName extends SimpleUmbaskaExpression<Faction>{
    Expression<String> name;
    @Override
    protected Faction[] get(Event event) {
        String name = this.name.getSingle(event);
        if (name == null){
            return null;
        }
        return new Faction[]{FactionsModule.getInstance().getFactionsImplementation().getByName(name)};
    }

    @Override
    public Class<? extends Faction> getReturnType() {
        return Faction.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) expressions[0];
        return true;
    }
}
