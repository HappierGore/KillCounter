package com.happiergore.killcounter;

import comandos.*;
import events.HeldItem;
import events.OnItemDespawn;
import events.OnSomeDeath;
import events.PlayerJoin;
import events.PlayerLeave;
import events.SomeDamaged;
import static helper.IOHelper.ExportResource;
import helper.TextUtils;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import mysqlite.MySQLite;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import placeholders.PlaceHolders;

/**
 *
 * @author HappierGore
 */
public final class EventListener extends JavaPlugin implements Listener {

    public static FileConfiguration configYML;

    @Override
    public void onEnable() {

        MySQLite.path = "jdbc:sqlite:" + dbPath.replace('\\', '/') + "/KillCounter.db";

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        configYML = getConfig();

        //Crear config.yml en caso de que no exista
        saveDefaultConfig();

        //Generar base de datos en caso de que no exista
        generateDB();

        //Si no se cumple con alguna dependencia, se cancela el plugin
        if (!checkDependencies()) {
            return;
        }

        //Comandos
        registerCommands();

        StringBuilder enabledMsg = new StringBuilder();
        enabledMsg.append("&9********************\n\n");
        enabledMsg.append("&bKillCounter cargado correctamente\n");
        enabledMsg.append("&b¡Gracias por tu preferencia!\n");
        enabledMsg.append("&6Autor: HappierGore");
        enabledMsg.append("&9Discord: &nHappierGore#\n\n");
        enabledMsg.append("&9********************\n");

        System.out.println(TextUtils.parseColor(enabledMsg.toString()));

        //Registrar eventos
        getServer().getPluginManager().registerEvents(this, this);
    }

    //****************
    // EVENTOS
    //****************
    String dbPath = getDataFolder().getAbsolutePath();

    @EventHandler
    public void EntityDeathEvent(EntityDeathEvent e) {
        OnSomeDeath.registerKill(e, dbPath);
    }

    @EventHandler
    public void PlayerItemHeldEvent(PlayerItemHeldEvent e) {
        HeldItem.loadItemData(e, dbPath);
    }

    @EventHandler
    public void EntityCombustEvent(EntityDamageEvent e) {
        SomeDamaged.untrackItem(e, dbPath);
    }

    @EventHandler
    public void ItemDespawnEvent(ItemDespawnEvent e) {
        OnItemDespawn.untrackItem(e, dbPath);
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        PlayerJoin.loadPlayerData(e, dbPath);
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        PlayerLeave.removePlayerFromMemory(e);

    }

    //********************
    //Own functions
    //********************
    private void generateDB() {
        String path = getDataFolder().getAbsolutePath();

        File dataBase = new File(path + "/KillCounter.db");

        if (!dataBase.exists()) {
            try {
                ExportResource("/KillCounter.db", path);
            } catch (Exception ex) {
                Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private boolean checkDependencies() {

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            System.out.println("******* ADVERTENCIA *******\n\nNo se encontró PlaceholderAPI, omitiendo...\n\n");
        } else {
            //Registrar placeholders
            new PlaceHolders().register();
        }
        if (getServer().getPluginManager().getPlugin("NBTAPI") == null) {
            System.out.println("************** ERROR **************\n\nNo se encontró NBTI API. Este plugin es necesario para funcionar.\nLink: https://www.spigotmc.org/resources/nbt-api.7939/\n\n***********************************");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    private void registerCommands() {
        this.getCommand("iteminfo").setExecutor(new SeeItemInfo());
    }
}
