package red.mirai.miraicombat.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import red.mirai.miraicombat.modules.PlayerDamageCalculation;

public class DamageApplicationEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamageEvent(final EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        Entity target = event.getEntity();
        final double damage = event.getFinalDamage();

        if ( attacker instanceof Player ) {
            PlayerDamageCalculation applyDamage = new PlayerDamageCalculation();
            Bukkit.getLogger().info( attacker.getName() + " -> Initial Damage: " + damage + ")" );

            applyDamage.calculate( event, damage, ( apply, finalHealth ) -> {
                if ( apply ) {
                    (( Damageable ) target).setHealth( finalHealth );
                }
                Bukkit.getLogger().info( attacker.getName() + " -> (" + apply + ", " + finalHealth + ")" );
            });

            event.setDamage( 0.01 );
        }
    }
}
