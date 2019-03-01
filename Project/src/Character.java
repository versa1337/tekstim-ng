public abstract class Character {

    public String name;
    public int health;
    public int maxHealth;

    public void GainHealth(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void TakeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            Die();
        }
    }

    public void Die() {

    }

}
