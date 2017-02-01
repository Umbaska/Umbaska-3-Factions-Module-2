package uk.co.umbaska.modules.factions.skript.expressions;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import uk.co.umbaska.modules.factions.FactionsModule;
import uk.co.umbaska.modules.factions.IFactionsPlugin;
import uk.co.umbaska.registration.UmbaskaPropertyExpression;
import uk.co.umbaska.registration.annotations.Name;
import uk.co.umbaska.registration.annotations.Syntaxes;
import uk.co.umbaska.modules.factions.skript.types.factions.Faction;

/**
 * @author Andrew Tran
 */
@Name("Faction Of Player")
@Syntaxes({"[the] (faction|kingdom|guild) of %player%", "%player%'[s] (faction|kingdom|guild)"})
public class ExprFactionOfPlayer extends UmbaskaPropertyExpression<Player, Faction>{
    Expression<Player> player;
    @Override
    protected Faction[] get(Event event, Player[] players) {
        return get(players, new Converter<Player, Faction>() {
            @Override
            public Faction convert(Player player) {
                if (player == null){
                    return null;
                }
                IFactionsPlugin factionsPlugin = FactionsModule.getInstance().getFactionsImplementation();
                return factionsPlugin.getFaction(player);
            }
        });
    }

    @Override
    public Class<? extends Faction> getReturnType() {
        return Faction.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) expressions[0];
        setExpr(player);
        return true;
    }
}
