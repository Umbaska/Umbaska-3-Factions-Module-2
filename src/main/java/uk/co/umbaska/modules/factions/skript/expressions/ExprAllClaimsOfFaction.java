package uk.co.umbaska.modules.factions.skript.expressions;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import uk.co.umbaska.modules.factions.FactionsModule;
import uk.co.umbaska.modules.factions.skript.types.claim.Claim;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;
import uk.co.umbaska.registration.SimpleUmbaskaExpression;
import uk.co.umbaska.registration.UmbaskaPropertyExpression;
import uk.co.umbaska.registration.annotations.Name;
import uk.co.umbaska.registration.annotations.Syntaxes;

/**
 * @author Andrew Tran
 */
@Name("All Claims Of Faction")
@Syntaxes({"[all] (claims|land|regions) (of|for) [faction|kingdom|guild] %umbfaction%",
            "%umbfaction%'[s] (claims|land|regions)"})
public class ExprAllClaimsOfFaction extends SimpleUmbaskaExpression<Claim> {
    Expression<Faction> faction;

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        faction = (Expression<Faction>) expressions[0];
        return true;
    }

    @Override
    protected Claim[] get(Event event) {
        Faction faction = this.faction.getSingle(event);
        if (faction == null){
            return null;
        }
        return FactionsModule.getInstance().getFactionsImplementation().getClaims(faction);
    }

    @Override
    public Class<? extends Claim> getReturnType() {
        return Claim.class;
    }
}
