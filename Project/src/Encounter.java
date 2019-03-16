public class Encounter {

    public enum Type { PATH, DUNGEON }

    private static final int pathEncounterAmount = 3;
    private static final int dungeonEncounterAmount = 1;

    private Type type;
    private int encounterNumber;

    public void GenerateRandom(Type type) {

        this.type = type;
        int random = 0;
        if (type == Type.PATH) {
            random = RNG.RandomInRange(1, pathEncounterAmount);
        } else if (type == Type.DUNGEON) {
            random = RNG.RandomInRange(1, dungeonEncounterAmount);
        }
        encounterNumber = random;

    }

    public void Call() {

        if (type == Type.PATH) {

            switch (encounterNumber) {
                case 1: MonsterEncounter();
                    break;
                case 2: DungeonEncounter();
                    break;
                case 3: FindingWeapon();
                    break;
            }

        } else if (type == Type.DUNGEON) {

            switch (encounterNumber) {
                case 1: FindingWeapon();
                    break;
            }

        }

    }

    ///////////////////////////////////////////////////// PATH ENCOUNTERS /////////////////////////////////////////////////////

    private void MonsterEncounter() {

        Monster monster = new Monster();
        monster.GenerateStats();
        CombatManager.StartBattle(monster);

    }

    private void DungeonEncounter() {

        Choice root = new Choice("You come across a dungeon entrance");
        Choice enter = root.AddChoice("Enter");
        Choice leave = root.AddChoice("Leave");

        enter.SetAction(() -> {
            System.out.println("Entered dungeon");
            Dungeon dungeon = new Dungeon();
            dungeon.Generate();
            dungeon.Enter();
        });

        root.Display();

    }

    private void FindingWeapon() {

        Weapon weapon = new Weapon("weapon", 1, 30);

        Choice root = new Choice("You find a " + weapon.name + "\nDamage: " + weapon.damage);
        Choice take = root.AddChoice("Take");
        Choice leave = root.AddChoice("Leave");

        take.SetAction(() -> {
            System.out.println("You picked up the " + weapon.name);
            //Player.instance.inventory.Add(weapon);
            Game.instance.NewEncounter();
        });

        root.Display();

    }

    ///////////////////////////////////////////////////// DUNGEON ENCOUNTERS /////////////////////////////////////////////////////

    private void Empty() {

        Choice root = new Choice("The room is empty");
        Choice proceed = root.AddChoice("Proceed");

    }

    private void Chest() {

        Choice root = new Choice("There is a chest in the middle of the room");
        Choice open = root.AddChoice("Open");
        Choice leave = root.AddChoice("Leave");

        open.SetAction(() -> {
            if (RNG.PercentageChance(50)) {

                System.out.println("A monster comes out of it!");
                MonsterEncounter();

            } else {
                System.out.println("It's empty");
            }
        });

        root.Display();

    }

}
