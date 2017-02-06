package neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingData {
    List<TrainingDataItem> trainingItems;
    public TrainingData(){
        trainingItems = new ArrayList<>();
        trainingItems.add(new TrainingDataItem(Arrays.asList(1.0,2.0,1.0,2.0,1.0,2.0,1.0,0.0,0.0), 1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,1.0,2.0,1.0,2.0,1.0,2.0,0.0,0.0), -1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,2.0,1.0,0.0,0.0,1.0,0.0,2.0,1.0), 1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,2.0,2.0,1.0,0.0,1.0,0.0,1.0,0.0), -1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,0.0,2.0,1.0,1.0,1.0,0.0,2.0,0.0), 1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(0.0,1.0,0.0,1.0,0.0,1.0,2.0,2.0,2.0), -1));
        trainingItems.add(new TrainingDataItem(Arrays.asList(2.0,0.0,1.0,0.0,2.0,0.0,1.0,1.0,2.0), -1));
    }

    public List<TrainingDataItem> getTrainingItems() {
        return trainingItems;
    }
}
