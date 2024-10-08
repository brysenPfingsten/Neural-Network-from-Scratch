import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class App {
    private static int correct = 0;
    private static int prevCorrect = 0;
    private static int epoch;
    private static double initialLearningRate = 0.01;
    private static double drop = 0.5;
    private static int epochsDrop = 5;

    public static void main(String[] args) throws Exception {
        int[][][] inputLayer = Mnist.readImages("C:\\Users\\bryse\\Desktop\\NeuralNetwork\\src\\train-images.idx3-ubyte");
        
        Layer hiddenLayer1 = new Layer(784,100);
        Layer hiddenLayer2 = new Layer(100,100);
        Layer hiddenLayer3 = new Layer(100, 100);

        Layer outputLayer = new Layer(100,10);

        Layer[] layers = {hiddenLayer1, hiddenLayer2, hiddenLayer3, outputLayer};

        String filename = "C:\\Users\\bryse\\Desktop\\NeuralNetwork\\output.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        TrainedLayer trainedHiddenLayer1 = new TrainedLayer(784,100);
        TrainedLayer trainedHiddenLayer2 = new TrainedLayer(100, 100);
        TrainedLayer trainedHiddenLayer3 = new TrainedLayer(100,100);
        TrainedLayer trainedOutputLayer = new TrainedLayer(100, 10);

        TrainedLayer[] trainedLayers = {trainedHiddenLayer1, trainedHiddenLayer2, trainedHiddenLayer3, trainedOutputLayer};

        for (int epochsss = 50; epochsss >= 0; epochsss--) {
            correct = 0;
            double learningRate = 0.001; //initialLearningRate * Math.pow(drop, Math.floor((1 + epoch) / epochsDrop));
            forwardPass(inputLayer, layers, learningRate);
            epoch++;

            System.out.println(String.format("%.2f%%",(correct * 100.0) / inputLayer.length));

            if (prevCorrect < correct) {
                for (int j = 0; j < layers.length; j++) {
                    trainedLayers[j].setWeightsAndBiases(layers[j].weights, layers[j].biases);
                }
            }
            prevCorrect = correct;
        }

        for (int index = 0; index < trainedLayers.length; index++) {
            if (index == trainedLayers.length -1) {
                trainedLayers[index].saveToFile("C:\\Users\\bryse\\Desktop\\NeuralNetwork\\trainedOutputLayer");
            }
            else
                trainedLayers[index].saveToFile("C:\\Users\\bryse\\Desktop\\NeuralNetwork\\trainedHiddenLayer" + index + 1);
        }

        writer.close();
        System.out.println("\n");
        // Test();
        
    }

    public static int Predict(double[] input) {
        TrainedLayer trainedHiddenLayer1 = new TrainedLayer(784,100);
        TrainedLayer trainedHiddenLayer2 = new TrainedLayer(100, 100);
        TrainedLayer trainedOutputLayer = new TrainedLayer(100, 10);
        trainedHiddenLayer1.loadFromFile("trainedHiddenLayer1");
        trainedHiddenLayer2.loadFromFile("trainedHiddenLayer2");
        trainedOutputLayer.loadFromFile("trainedOutputLayer");
        TrainedLayer[] layers = {trainedHiddenLayer1, trainedHiddenLayer2, trainedOutputLayer};

        double[] current = input;

        System.out.println(Arrays.toString(input));

        for (TrainedLayer layer : layers) {
            current = LinearAlgebra.matrixMultiplication(layer.weights, current);
            current = LinearAlgebra.addVectors(layer.biases, current);
            current = LinearAlgebra.sigmoid(current);
        }

        return getPrediction(current);
    }

    public static void Test() throws IOException {
        int testCorrect = 0;
        int[] lables = Mnist.readLabels("C:\\Users\\bryse\\Desktop\\NeuralNetwork\\src\\t10k-labels.idx1-ubyte");
        int[][][] testData = Mnist.readImages("C:\\Users\\bryse\\Desktop\\NeuralNetwork\\src\\t10k-images.idx3-ubyte");
        
        TrainedLayer trainedHiddenLayer1 = new TrainedLayer(784,16);
        TrainedLayer trainedHiddenLayer2 = new TrainedLayer(16, 16);
        TrainedLayer trainedOutputLayer = new TrainedLayer(16, 10);
        trainedHiddenLayer1.loadFromFile("trainedHiddenLayer1");
        trainedHiddenLayer2.loadFromFile("trainedHiddenLayer2");
        trainedOutputLayer.loadFromFile("trainedOutputLayer");
        TrainedLayer[] layers = {trainedHiddenLayer1, trainedHiddenLayer2, trainedOutputLayer};

        String filename = "C:\\Users\\bryse\\Desktop\\NeuralNetwork\\output.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        for (int i = 0; i < testData.length; i++) {
            double[] doubleColumnImageData = convertInt2DToDouble1D(testData[i]);

            double[] current = doubleColumnImageData;

            for (TrainedLayer layer : layers) {
                current = LinearAlgebra.matrixMultiplication(layer.weights, current);
                current = LinearAlgebra.addVectors(layer.biases, current);
                current = LinearAlgebra.sigmoid(current);
            }

            if (getPrediction(current) == lables[i]) {
                testCorrect++;
            }

            if (getPrediction(current) != lables[i]) {
                writer.write("\n\nLabel: " + lables[i]);
                writer.write("\nPrediction: " + getPrediction(current));
                for (int j = 0; j < current.length; j++) {
                    writer.write("\n" + j + ": " + current[j]);
                }
            }

        
        }
        writer.close();
        System.out.println(String.format("%.2f%%", testCorrect * 100.0 / testData.length));
    }

    public static void calcHiddenNodeValues(Layer[] layers) {
        for (int i = layers.length - 2; i >= 0; i--) { // reverse for loop
            layers[i].calcHiddenNodeValue(layers[i+1]);
        }
    }

    public static void calcGradients(Layer[] layers, double[] inputActivations) {
        for (int i = layers.length - 1; i >= 0; i--)
            if (i == 0)
                layers[i].calcGradients(inputActivations);
            else 
                layers[i].calcGradients(layers[i-1].activationValues);
    }

    public static void applyGradients(Layer[] layers, double learningRate) {
        for (int i = layers.length - 1; i >= 0; i--)
            layers[i].applyGradient(learningRate);
    }

    public static int getPrediction(double[] array) {
    
        int maxIndex = 0;
        double maxValue = array[0];
    
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }
    
        return maxIndex;
    }

    public static double[] convertInt2DToDouble1D(int[][] intArray) {
        int rows = intArray.length;
        int cols = intArray[0].length;
        double[] doubleArray = new double[rows * cols];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                doubleArray[index++] = (double) intArray[i][j];
            }
        }

        return doubleArray;
    }

    public static void forwardPass(int[][][] inputLayer, Layer[] layers, double learningRate) throws IOException {

        int[] lables = Mnist.readLabels("C:\\Users\\bryse\\Desktop\\NeuralNetwork\\src\\train-labels.idx1-ubyte");

        for (int i = 0; i < inputLayer.length; i++) {
            double[] doubleColumnImageData = convertInt2DToDouble1D(inputLayer[i]);

            double[] current = doubleColumnImageData;

            for (int l = 0; l < layers.length; l++) {
                current = LinearAlgebra.matrixMultiplication(layers[l].weights, current);
                current = LinearAlgebra.addVectors(layers[l].biases, current);
                current = LinearAlgebra.sigmoid(current);
                layers[l].activationValues = current;
                }

            backPropagate(layers, doubleColumnImageData, lables[i], learningRate);

            if (getPrediction(current) == lables[i]) {
                correct++;}
            }
    }

    

    public static void backPropagate(Layer[] layers, double[] doubleColumnImageData, int expected, double learningRate) {
        layers[layers.length-1].calcOutputNodeValue(expected);
        calcHiddenNodeValues(layers);
        calcGradients(layers, doubleColumnImageData);
        applyGradients(layers, learningRate);
    }
}