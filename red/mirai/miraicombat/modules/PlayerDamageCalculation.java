package red.mirai.miraicombat.modules;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import red.mirai.miraicombat.MiraiCombat;

import java.util.function.BiConsumer;

public class PlayerDamageCalculation {
    public void calculate( EntityDamageByEntityEvent event, final double damage, BiConsumer<Boolean, Double> callback ) {
        new BukkitRunnable(){
            Entity attacker = event.getDamager();
            Entity target = event.getEntity();

            double newDamage = 0;
            double finalDamage = event.getFinalDamage();
            double damageDiff = Math.abs( damage - finalDamage );

            @Override
            public void run(){
                if ( damage < 0.90D || !(attacker instanceof Player && target instanceof Damageable) ) {
                    Bukkit.getLogger().info( attacker.getName() + " -> Damage Check Fail: ( " + damage + ", " + (attacker instanceof Player) +
                            ", " + (target instanceof Damageable) + ")");
                    callback.accept( false, 0D );
                    return;
                }

                Player ply = ( Player ) attacker;
                AttributeInstance plyAttributeInst = ply.getAttribute( Attribute.GENERIC_ATTACK_DAMAGE );

                if ( plyAttributeInst == null ) {
                    callback.accept( false, 0D );
                    return;
                }

                newDamage = plyAttributeInst.getValue();
                finalDamage = newDamage - damageDiff;

                Damageable damageableTarget = (Damageable) target;
                double targetHealth = damageableTarget.getHealth();

                double finalHealth = Math.max(0, Math.min(15, targetHealth - finalDamage));
                Bukkit.getLogger().info( attacker.getName() + " has done " + finalDamage + " to " + target.toString() );
                callback.accept( true, finalHealth );
            }
        }.runTaskAsynchronously( MiraiCombat.plugin );
    }
}
