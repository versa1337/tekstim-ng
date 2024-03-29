public class Game {

    public static Game instance;

    public Game() {
        instance = this;
    }

    public void Start() {

        Sound.PlayMusic("Wander_Around_song");

        MonsterFactory.Setup();
        ItemFactory.Setup();
        NewEncounter();

    }

    public void NewEncounter() {

        Choice root = new Choice("");
        Choice proceed = new Choice("Follow the road");
        root.AddChoice(proceed);

        proceed.SetAction(() -> {
            Encounter encounter = new Encounter();
            encounter.GenerateRandom(Encounter.Type.PATH);
            encounter.PathEncounter();
        });

        root.Display();

    }

}
