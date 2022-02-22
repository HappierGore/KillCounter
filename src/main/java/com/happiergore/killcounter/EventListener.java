package com.happiergore.killcounter;

import comandos.*;
import events.HeldItem;
import events.OnSomeDeath;
import events.SomeDamaged;
import static helper.IOHelper.ExportResource;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.java.JavaPlugin;
import placeholders.PlaceHolders;

/**
 *
 * @author HappierGore
 */
public final class EventListener extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

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

        //Registrar placeholders
        new PlaceHolders().register();

        System.out.println("Cargado correctamente!");

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
        HeldItem.helding(e, dbPath);
    }

    @EventHandler
    public void EntityCombustEvent(EntityDamageEvent e) {
        SomeDamaged.onDamaged(e, dbPath);
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
            System.out.println("No se encontró PlaceholderAPI, este plugin es necesario.");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        return true;
    }

    private void registerCommands() {
        this.getCommand("newItem").setExecutor(new NewItem());
        this.getCommand("iteminfo").setExecutor(new SeeItemInfo());
    }
}
