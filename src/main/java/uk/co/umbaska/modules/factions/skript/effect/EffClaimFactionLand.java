package uk.co.umbaska.modules.factions.skript.effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.event.Event;
import uk.co.umbaska.modules.factions.FactionsModule;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;
import uk.co.umbaska.registration.UmbaskaEffect;
import uk.co.umbaska.registration.annotations.Name;
import uk.co.umbaska.registration.annotations.PropertyExpressionType;
import uk.co.umbaska.registration.annotations.Syntaxes;

/**
 * @author Andrew Tran
 */
@Name("Claim Land For Faction")
@Syntaxes({"(claim|acquire|get) (land|chunk|region) for [faction|kingdom|guild] %umbfaction% at %location%",
            "(claim|acquire|get) (land|chunk|region) at %location% for [faction|kingdom|guild] %umbfaction%"})
public class EffClaimFactionLand extends UmbaskaEffect{
    Expression<Faction> faction;
    Expression<Location> location;
    @Override
    protected void execute(Event event) {
        Faction faction = this.faction.getSingle(event);
        Location location = this.location.getSingle(event);
        if (faction == null || location == null){
            return;
        }
        FactionsModule.getInstance().getFactionsImplementation().claim(faction, location);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        int factionIndex = matched == 0 ? 0 : 1;
        int locationIndex = matched == 0 ? 1 : 0;

        faction = (Expression<Faction>) expressions[factionIndex];
        location = (Expression<Location>) expressions[locationIndex];

        return true;
    }
}
