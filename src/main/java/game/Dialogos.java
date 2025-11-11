package game;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;

// Clases POJO para que Gson lea el dialogos.json

class GameData {
    Map<String, Scene> scenes;
}

class Scene {
    String id;
    String title;
    List<String> description;
    List<DialogueLine> dialogues;

    @SerializedName("description_after_dialogue")
    List<String> descriptionAfterDialogue;

    List<DialogueLine> dialogues_2;

    @SerializedName("description_after_dialogue_2")
    List<String> descriptionAfterDialogue2;

    List<DialogueLine> dialogues_3;

    @SerializedName("description_after_dialogue_3")
    List<String> descriptionAfterDialogue3;

    String prompt;
    List<SceneOption> options;
    InitialState initialState;

    boolean endOfDay;
    boolean endGame;
    List<String> summary;
    String nextScene;
}

class DialogueLine {
    String speaker;
    String line;
}

class SceneOption {
    String text;
    Outcome outcome;
    String precondition;
}

class Outcome {
    List<String> description;
    List<DialogueLine> dialogues;
    Effect effect;
    String nextScene;
}

class Effect {
    int healthChange;
    String message;
    List<String> items;
    String state;
    String unlockAchievement;
}

class InitialState {
    int health;
    int health_max;
    String status;
}