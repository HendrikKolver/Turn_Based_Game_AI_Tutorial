package neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingData {
    private static List<TrainingDataItem> trainingItems;

    //A basic set of win and lose cases
    //1 Represents a player position
    //2 Represents a opponent position
    //0 Represents a open board position
    //-1 for a loss, 1 for a win, 0 for a tie
    public static List<TrainingDataItem> getTrainingItems() {
        trainingItems = new ArrayList<>();
        trainingItems.add(new TrainingDataItem(Arrays.asList(1.0,2.0,1.0,2.0,1.0,2.0,1.0,0.0,0.0), 1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,1.0,2.0,1.0,2.0,1.0,2.0,0.0,0.0), -1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,2.0,1.0,0.0,0.0,1.0,0.0,2.0,1.0), 1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,2.0,2.0,1.0,0.0,1.0,0.0,1.0,0.0), -1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,0.0,2.0,1.0,1.0,1.0,0.0,2.0,0.0), 1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(0.0,1.0,0.0,1.0,0.0,1.0,2.0,2.0,2.0), -1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,0.0,1.0,0.0,2.0,0.0,1.0,1.0,2.0), -1));
        return trainingItems;
    }
}
