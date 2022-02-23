package kills;

import org.bukkit.entity.EntityType;

/**
 *
 * @author HappierGore
 */
public abstract class Kills {

    private int players = 0;
    private int zombies = 0;

    private int totalMobs = 0;

    private final String UUID;

    //*****************************
    //INTERFACE
    //*****************************
    public void add(EntityType entityType) {
        switch (entityType) {
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

    public abstract void updateDB(String dbPath);

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
