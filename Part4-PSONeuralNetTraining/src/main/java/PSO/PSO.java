package PSO;

import neuralnetwork.NeuralNetwork;
import player.NeuralNetPlayer;

import java.util.ArrayList;
import java.util.List;

public class PSO {
    private static final double LEARNING_FACTOR = 1.4;
    private static final double MAX_WEIGHT = 1;
    private int playerCount = 10;
    private int hiddenNeuroncount = 4;
    private int roundCount = 200;
    private final double MAX_VELOCITY = 0.3;
    private final double VELOCITY_WEIGHT = 0.72;


    public NeuralNetPlayer trainPlayer(){
        List<NeuralNetPlayer> players = getSeedPlayers();

        float roundCounter = 0;

        while(roundCounter < roundCount){
            System.out.println("Percentage complete: "+ Math.round(roundCounter/roundCount*100f));

            Tournament.runTournament(players);
            updatePersonalBests(players);
            NeuralNetPlayer player = getBestPlayer(players);
            updateVelocities(players, player);
            updatePlayerPositions(players);

            //Clear all player scores after every round
            clearCurrentScores(players);

            roundCounter++;
        }

        //Run the tournament one last time to update the personal bests
        Tournament.runTournament(players);
        updatePersonalBests(players);

        return getBestPlayer(players);
    }

    private void clearCurrentScores(List<NeuralNetPlayer> players) {
        for (NeuralNetPlayer player : players) {
            player.setCurrentScore(0);
        }
    }

    private void updatePlayerPositions(List<NeuralNetPlayer> players) {
        for (NeuralNetPlayer player : players) {
            List<Double> velocities = player.getVelocity();
            NeuralNetwork currentSolution = player.getNeuralNetwork();
            int velocityCount = 0;

            for (int i = 0; i < currentSolution.getInputToHiddenLayerWeights().size(); i++) {
                currentSolution.getInputToHiddenLayerWeights().set(i, velocities.get(velocityCount)+ currentSolution.getInputToHiddenLayerWeights().get(i));

                //Stop the solution from leaving the search space
                if(currentSolution.getInputToHiddenLayerWeights().get(i) > MAX_WEIGHT){
                    currentSolution.getInputToHiddenLayerWeights().set(i,MAX_WEIGHT);
                    velocities.set(velocityCount, 0.0);
                }else if(currentSolution.getInputToHiddenLayerWeights().get(i) < MAX_WEIGHT*-1){
                    currentSolution.getInputToHiddenLayerWeights().set(i,MAX_WEIGHT*-1);
                    velocities.set(velocityCount, 0.0);
                }
                velocityCount++;
            }

            for (int i = 0; i < currentSolution.getHiddenToOutputLayerWeights().size(); i++) {
                currentSolution.getHiddenToOutputLayerWeights().set(i, velocities.get(velocityCount)+ currentSolution.getHiddenToOutputLayerWeights().get(i));

                //Stop the solution from leaving the search space
                if(currentSolution.getHiddenToOutputLayerWeights().get(i) > MAX_WEIGHT){
                    currentSolution.getHiddenToOutputLayerWeights().set(i,MAX_WEIGHT);
                    velocities.set(velocityCount, 0.0);
                }else if (currentSolution.getHiddenToOutputLayerWeights().get(i) < MAX_WEIGHT*-1){
                    currentSolution.getHiddenToOutputLayerWeights().set(i,MAX_WEIGHT*-1);
                    velocities.set(velocityCount, 0.0);
                }
                velocityCount++;
            }
        }
    }

    private void updateVelocities(List<NeuralNetPlayer> players, NeuralNetPlayer globalBestPlayer) {
       //calculate velocity for each player
        for (NeuralNetPlayer player : players) {
            List<Double> velocities = player.getVelocity();
            NeuralNetwork currentSolution = player.getNeuralNetwork();
            NeuralNetwork personalBestSolution = player.getPersonalBestNetwork();
            //Use the personal best neural network of the global best player as this is likely to represent the best solution that has been found so far
            NeuralNetwork globalBestSolution = globalBestPlayer.getPersonalBestNetwork();

            //Calculate velocity for each dimension
            for (int i = 0; i < velocities.size(); i++) {
                //Calculating the personal and global components of the new velocity
                //The personal component represents the particles personal best solution
                //The global component represents the global best solution
                double personalComponent = calculateComponent(personalBestSolution, currentSolution, i);
                double globalComponent = calculateComponent(globalBestSolution, currentSolution, i);

                //Using the average velocity of the particle as its current momentum
                double newVelocity = getNewVelocity((VELOCITY_WEIGHT* getCurrentMomentum(velocities))+ personalComponent + globalComponent);
                velocities.set(i, newVelocity);
            }
        }
    }

    private double getNewVelocity(double newVelocity) {
        //Capping the velocity in any dimension to a max
        if(newVelocity> MAX_VELOCITY){
            newVelocity = MAX_VELOCITY;
        }else if( newVelocity < (-1 * MAX_VELOCITY)){
            newVelocity = (-1 * MAX_VELOCITY);
        }
        return newVelocity;
    }

    private double getCurrentMomentum(List<Double> velocities) {
        double total = 0;
        for (Double velocity: velocities) {
            total += velocity;
        }
        return total/velocities.size();
    }


    private double calculateComponent(NeuralNetwork comparingSolution, NeuralNetwork currentSolution, int i) {
        double comparingSolutionWeight;
        double currentSolutionWeight;

        //Get the weights we are working with
        if(i < currentSolution.getInputToHiddenLayerWeights().size()){
            comparingSolutionWeight = comparingSolution.getInputToHiddenLayerWeights().get(i);
            currentSolutionWeight = currentSolution.getInputToHiddenLayerWeights().get(i);
        }else{
            comparingSolutionWeight = comparingSolution.getHiddenToOutputLayerWeights().get(i- comparingSolution.getInputToHiddenLayerWeights().size());
            currentSolutionWeight = currentSolution.getHiddenToOutputLayerWeights().get(i - currentSolution.getInputToHiddenLayerWeights().size());
        }

        return ((comparingSolutionWeight - currentSolutionWeight) * LEARNING_FACTOR * Math.random());
    }

    private void updatePersonalBests(List<NeuralNetPlayer> players) {
        for (NeuralNetPlayer player : players) {
            player.updatePersonalBest();
        }
    }

    private List<NeuralNetPlayer> getSeedPlayers() {
        List<NeuralNetPlayer> seedPlayers = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            seedPlayers.add(new NeuralNetPlayer(new NeuralNetwork(hiddenNeuroncount)));
        }
        return seedPlayers;
    }

    private NeuralNetPlayer getBestPlayer(List<NeuralNetPlayer> currentTournamentPlayers){
        //Run a full round of the tournament using each players personal best solution to find the absolute best solution in the pool
        List<NeuralNetPlayer> bestSolutionPlayers = new ArrayList<>();
        for (NeuralNetPlayer player : currentTournamentPlayers) {
            bestSolutionPlayers.add(new NeuralNetPlayer(player.getPersonalBestNetwork()));
        }

        Tournament.runTournament(bestSolutionPlayers);

        int maxScore = -10000;
        NeuralNetPlayer winningPlayer = null;
        int counter = 0;

        for (NeuralNetPlayer bestSolutionPlayer : bestSolutionPlayers) {
            if (bestSolutionPlayer.getCurrentScore() > maxScore) {
                maxScore = bestSolutionPlayer.getCurrentScore();
                winningPlayer = currentTournamentPlayers.get(counter);
                counter++;
            }
        }
        return winningPlayer;
    }
}
