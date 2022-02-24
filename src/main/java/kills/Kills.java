package kills;

import org.bukkit.event.entity.EntityDeathEvent;

/**
 *
 * @author HappierGore
 */
public abstract class Kills {

    private int players = 0;
    private int zombies = 0;

    private int totalMobs = 0;

    private final String UUID;

    /**
     * Añade 1 al contador de kills del jugador u objeto
     *
     * @param e Tipo de entidad a añadir al contador
     */
    public void add(EntityDeathEvent e) {

        switch (e.getEntityType()) {
            case PLAYER -> {
                totalMobs++;
                players++;
            }
            case ZOMBIE -> {
                totalMobs++;
                zombies++;
            }
            default -> {
                totalMobs++;
            }
        }
    }

    /**
     * Permite sobreescribir los datos locales por los de una base de datos.
     *
     * @param mobType Recibe el tipo de mob o dato a cargar de la base de datos.
     * @param amount Recibe la cantidad de kills a establecer.
     */
    public void loadFromDB(String mobType, int amount) {
        switch (mobType.toLowerCase()) {
            case "zombies": {
                this.zombies = amount;
            }
            case "totalmobs": {
                this.totalMobs = amount;
            }
            case "players": {
                this.players = amount;
            }
        }
    }

    /**
     * Actualizará la base de datos de acuerdo a los valores locales del jugador
     * u objeto
     *
     */
    public abstract void updateDB();

    public String getUUID() {
        return this.UUID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Kills{");
        sb.append("totalMobs=").append(totalMobs);
        sb.append(", players=").append(players);
        sb.append(", zombies=").append(zombies);
        sb.append(",Memory=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }

    //Constructor
    public Kills(String UUID) {
        this.UUID = UUID;
    }

    //Getters
    public int getPlayers() {
        return players;
    }

    public int getZombies() {
        return zombies;
    }

    public int getTotalMobs() {
        return totalMobs;
    }

}
