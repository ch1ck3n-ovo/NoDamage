package tw.ch1ck3n.plugin;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoDamage extends JavaPlugin implements Listener {

    private boolean block_break, player_damage;

    private final String BOLD = "§l", RED = "§c", RESET = "§r";

    private final String MARK = BOLD + RED + "(!)" + RESET + " ";

    private final String block_break_message = MARK + "Sorry, but you can't break block here.";

    private final String player_damage_message = MARK + "You takes " + "DAMAGE" + RED + "❤" + RESET + " of damage.";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        getServer().getPluginManager().registerEvents(this, this);

        block_break = config.getBoolean("block_break");
        player_damage = config.getBoolean("player_damage");
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE && e.getPlayer().isOp()) return;

        if (block_break) {
            message(e.getPlayer(), block_break_message);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            if (player_damage) {
                message((Player) e.getEntity(), player_damage_message.replace("DAMAGE", String.valueOf(e.getDamage())));
                e.setDamage(0);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            if (player_damage) {
                message((Player) e.getEntity(), player_damage_message.replace("DAMAGE", String.valueOf(e.getDamage())));
                e.setDamage(0);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            if (player_damage) {
                message((Player) e.getEntity(), player_damage_message.replace("DAMAGE", String.valueOf(e.getDamage())));
                e.setDamage(0);
            }
        }
    }

    public void message(Player player, String message) {
        player.spigot().sendMessage(new TextComponent(message));
    }
}
