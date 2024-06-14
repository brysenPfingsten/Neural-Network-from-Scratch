import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        int[][][] inputLayer = Mnist.readImages("src\\train-images.idx3-ubyte");
        

        Layer hiddenLayer1 = new Layer(784,16);
        Layer hiddenLayer2 = new Layer(16,16);

        Layer outputLayer = new Layer(16,10);

        Layer[] layers = {hiddenLayer1, hiddenLayer2, outputLayer};
        forwardPass(inputLayer, layers);
    }

    public static void calcHiddenNodeValues(Layer[] layers) {
        for (int i = layers.length - 2; i >= 0; i--) { // reverse for loop
            layers[i].calcHiddenNodeValue(layers[i+1]);
        }
    }

    public static void forwardPass(int[][][] inputLayer, Layer[] layers) throws IOException {

        int[] lables = Mnist.readLabels("src\\train-labels.idx1-ubyte");
        

        for (int i = 0; i < inputLayer.length; i++) {
            int[][] imageData = inputLayer[i];
            int[] columnImageData = new int[784];
            for (int j = 0; j < imageData.length; j++) {
                for (int k = 0; k < imageData.length; k++) {
                    columnImageData[j*28+k] = imageData[j][k];
                }
            }

            double[] current = new double[columnImageData.length];
            for (int x = 0; x < columnImageData.length; x++) {
                current[x] = (double) columnImageData[x];
        }

            current = LinearAlgebra.sigmoid(current);

            for (int l = 0; l < layers.length; l++) {
                current = LinearAlgebra.matrixMultiplication(layers[l].weights, current);
                current = LinearAlgebra.vectorAddition(layers[l].biases, current);
                current = LinearAlgebra.sigmoid(current);
                if (l == layers.length - 1)
                    layers[l].calcOutputNodeValue(lables[i]);
                else 
                    layers[l+1].activationValues = current;
                }

            calcHiddenNodeValues(layers);

            System.out.println("\nLabel: " + lables[i]);
            for (int m = 0; m < current.length; m++) {
                System.out.println(m + ": " + current[m] + " ");
            }
            System.out.println("Loss: " + MyMath.OutputCost(lables[i], current));
            System.out.println(layers[1].toString());
            System.out.println(layers[2].toString());
        }
    }
}