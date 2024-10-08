import java.util.Random;

public class Layer {
    public int inputSize;
    public int outputSize;
    public double[][] weights;
    public double[] biases;
    public double[] activationValues;
    public double[] nodeValues;
    public double[][] gradients;
    
    private double[][] weightVelocities;
    private double[] biasVelocities;
    private double momentum = 0.9; // Typically between 0.9 and 0.99

    public Layer(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.weights = new double[outputSize][inputSize];
        this.biases = new double[outputSize];
        this.activationValues = new double[outputSize];
        this.nodeValues = new double[outputSize];
        this.gradients = new double[outputSize][inputSize];
        this.weightVelocities = new double[outputSize][inputSize];
        this.biasVelocities = new double[outputSize];
        initParameters();
    }

    public void setWeights(double[][] givenWeights) {
        weights = givenWeights;
    }

    public void setBiases(double[] givenBiases) {
        biases = givenBiases;
    }

    public void initParameters() {
        double limit = Math.sqrt(6.0 / (inputSize + outputSize));
        Random rand = new Random();
        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                weights[i][j] = rand.nextDouble() * 2 * limit - limit;
            }
            biases[i] = 0;
        }
    }

    public void calcOutputNodeValue(int expectedOutput) {
        for (int i = 0; i < outputSize; i++) {
            nodeValues[i] = Calculus.CostDerivative(activationValues[i], i == expectedOutput ? 1 : 0) * Calculus.SigmoidDerivative(activationValues[i]);
        }
    }

    public void calcHiddenNodeValue(Layer postLayer) {
        for (int i = 0; i < outputSize; i++) {
            double tempVal = 0;
            for (int j = 0; j < postLayer.weights.length; j++) {
                tempVal += postLayer.weights[j][i] * postLayer.nodeValues[j];
            }
            nodeValues[i] = tempVal * Calculus.SigmoidDerivative(activationValues[i]);}
        }

    public void calcGradients(Layer prevLayer) {
        for (int i = 0; i < gradients.length; i++) {
            for (int j = 0; j < gradients[0].length; j++) {
                gradients[i][j] = 0;
                for (int a = 0; a < prevLayer.activationValues.length; a++) {
                    gradients[i][j] += prevLayer.activationValues[a] * nodeValues[i];
                }
            }
        }
    }

    public void calcGradients(double[] prevLayerActivations) {
        for (int i = 0; i < gradients.length; i++) {
            for (int j = 0; j < gradients[0].length; j++) {
                gradients[i][j] = prevLayerActivations[j] * nodeValues[i];
            }
        }
    }

    public void applyGradient(double learningRate) {
        weights = LinearAlgebra.addMatrices(weights, LinearAlgebra.scaleMatrix(gradients, -learningRate));
        biases = LinearAlgebra.addVectors(biases, LinearAlgebra.scaleVector(nodeValues, -learningRate));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Layer {\n");
        sb.append("  inputSize: ").append(inputSize).append(",\n");
        sb.append("  outputSize: ").append(outputSize).append(",\n");
        sb.append("  weights: {\n");
        for (int i = 0; i < outputSize; i++) {
            sb.append("    {");
            for (int j = 0; j < inputSize; j++) {
                sb.append(String.format("%.12f", weights[i][j]));
                if (j < inputSize - 1) {
                    sb.append(", ");
                }
            }
            sb.append("}");
            if (i < outputSize - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("};\n");
        sb.append("  biases: {");
        for (int i = 0; i < outputSize; i++) {
            sb.append(String.format("%.12f", biases[i]));
            if (i < outputSize - 1) {
                sb.append(", ");
            }
        }
        sb.append("};\n");
        sb.append("  activationValues: [");
        for (int i = 0; i < activationValues.length; i++) {
            sb.append(String.format("%.4f", activationValues[i]));
            if (i < activationValues.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("],\n");
        sb.append("  nodeValues: [");
        for (int i = 0; i < nodeValues.length; i++) {
            sb.append(String.format("%.4f", nodeValues[i]));
            if (i < nodeValues.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("],\n");
        sb.append("  gradients: [");
        for (int i = 0; i < outputSize; i++) {
            sb.append("    [");
            for (int j = 0; j < inputSize; j++) {
                sb.append(String.format("%.4f", gradients[i][j]));
                if (j < inputSize - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i < outputSize - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]\n");
        sb.append("}");
        return sb.toString();
    }
}
