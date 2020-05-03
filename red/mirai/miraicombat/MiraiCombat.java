package red.mirai.miraicombat;

import org.bukkit.plugin.java.JavaPlugin;
import red.mirai.miraicombat.events.DamageApplicationEvent;

public final class MiraiCombat extends JavaPlugin {
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents( new DamageApplicationEvent(), this );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
