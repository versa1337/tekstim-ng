public class Player extends Character {

    public int xp = 0;
    public int level = 1;
    public int armor = 0;
    public Inventory inventory;
    public Weapon equippedWeapon;

    public static Player instance;

    public Player(String name) {
        instance = this;

        this.name = name;
        maxHealth = 100;
        health = maxHealth;
    }

    public void GainXP(int amount) {

        xp += amount;
        System.out.println("Gained " + amount + " xp!");
        if (xp >= level * 10) {
            xp -= level * 10;
            level++;
            System.out.println("LEVEL UP!\nLevel: " + level);
        }

    }

    public void Die() {

        Game.instance.isRunning = false;
        System.out.println("You died.");

    }

}
