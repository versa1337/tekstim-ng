public class CombatManager {

    public static Monster monster;
    private static Room room;

    public static void StartBattle(Monster m) {
        monster = m;
        Start();
    }

    public static void StartBattle(Monster m, Room r) {
        monster = m;
        room = r;
        Start();
    }

    private static void Start() {
        Sound.PlaySound("monsters/" + monster.name + "/play");
        Controller.instance.StartBattle(monster);
        //BattleLoop();
    }

    public static void BattleLoop() {

        Choice root = new Choice("");
        Choice attack = root.AddChoice("Attack");

        attack.SetAction(() -> {

            AttackMonster();

            if (!monster.isAlive) {
                MonsterDeath();
                return;
            }

            //AttackPlayer();
            //BattleLoop();

        });

        root.Display();

    }

    private static void AttackMonster() {

        Controller.instance.PlayerAttackAnimation();
        Sound.PlaySound("hitsound");

        Weapon weapon = Player.instance.GetEquippedWeapon();

        int damage = weapon.damage;

        if (RNG.PercentageChance(weapon.critChance)) {
            System.out.println("Critical hit! Double damage.");
            Controller.instance.DisplayMessage("CRITICAL HIT", "critical_hit");
            damage *= 2;
        }

        monster.TakeDamage(damage);

        Controller.instance.UpdateMonsterStats(monster);

    }

    public static void AttackPlayer() {

        if (monster.ability == Monster.Ability.Regeneration) {
            monster.GainHealth(monster.maxHealth / 2);
            //Controller.instance.MonsterRegenAnimation();
        }

        //Sound.PlaySound("monsters/" + monster.name + "/attack", false);
        //Controller.instance.MonsterAttackAnimation();
        Sound.PlaySound("hitsound");

        int damage = monster.damage;

        if (RNG.PercentageChance(2)) {
            System.out.println("Critical hit! Double damage.");
            Controller.instance.DisplayMessage("CRITICAL HIT", "critical_hit");
            damage *= 2;
        }

        if (Player.instance.shield != null) {
            if (RNG.PercentageChance(Player.instance.shield.blockChance)) {
                System.out.println("Blocked the attack with your shield!");
                Controller.instance.ShieldBlock();
                return;
            }
        }

        Player.instance.TakeDamage(damage);

        if (monster.ability == Monster.Ability.Lifesteal) {
            monster.GainHealth(damage);
        }

    }

    private static void MonsterDeath() {

        Controller.instance.EndBattle();
        Player.instance.GainXP(monster.maxHealth);
        Player.instance.RefillMana();

        Sound.PlaySound("monsters/" + monster.name + "/death");
        if (monster.ability == Monster.Ability.Deathrattle) {
            Monster newMonster = new Monster(" ", 1, 1, null);
            if (monster.name.equals("Splitting Festeroot")) {
                newMonster = MonsterFactory.GetMonster("Splitting Sapling");
            } else if (monster.name.equals("Splitting Sapling")) {
                newMonster = MonsterFactory.GetMonster("Woodchip");
            }
            CombatManager.StartBattle(newMonster);
            //Controller.instance.DeathrattleAnimation();
        } else if (room != null) {
            room.hasBeenVisited = true;
            room.Enter();
        } else {
            Game.instance.NewEncounter();
        }

    }

    /*
    private static void TryEscape(Monster monster) {

        if (monster.health * monster.damage > Player.instance.level * Player.instance.health) {

            System.out.println("You ran away!");

        } else {

            System.out.println("Failed to run away - the enemy is too strong!");

        }

    }
    */

}
