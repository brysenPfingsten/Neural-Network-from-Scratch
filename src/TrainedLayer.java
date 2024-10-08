import java.io.*;
import java.util.Arrays;

public class TrainedLayer {
    int inputSize;
    int outputSize;
    double[][] weights;
    double[] biases;

    public TrainedLayer(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.weights = new double[outputSize][inputSize];
        this.biases = new double[outputSize];
    }

    public void setWeightsAndBiases(double[][] weights, double[] biases) {
        if (weights.length != outputSize || weights[0].length != inputSize) {
            throw new IllegalArgumentException("Invalid weights dimensions");
        }
        if (biases.length != outputSize) {
            throw new IllegalArgumentException("Invalid biases dimensions");
        }
        this.weights = weights;
        this.biases = biases;
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(weights);
            oos.writeObject(biases);
            System.out.println("TrainedLayer saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving TrainedLayer: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            weights = (double[][]) ois.readObject();
            biases = (double[]) ois.readObject();
            System.out.println("TrainedLayer loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading TrainedLayer: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "TrainedLayer{" +
                "inputSize=" + inputSize +
                ", outputSize=" + outputSize +
                ", weights=" + Arrays.deepToString(weights) +
                ", biases=" + Arrays.toString(biases) +
                '}';
    }
}
