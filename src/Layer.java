public class Layer {
    public int inputSize;
    public int outputSize;
    public double[][] weights;
    public double[] biases;
    public double[] activationValues;
    public double[] nodeValues;
    public double[][] gradients;

    public Layer(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.weights = new double[outputSize][inputSize];
        this.biases = new double[outputSize];
        this.activationValues = new double[outputSize];
        this.nodeValues = new double[outputSize];
        this.gradients = new double[outputSize][inputSize];
        initParameters();
    }

    public void initParameters() {
        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                weights[i][j] = Math.random() / 100;
            }
            biases[i] = Math.random() / 100;
        }
    }

    public void calcOutputNodeValue(int expectedOutput) {
        for (int i = 0; i < outputSize; i++) {
            nodeValues[i] = Calculus.CostDerivative(activationValues[i], expectedOutput) * Calculus.SigmoidDerivative(activationValues[i]);
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

    public void calcGradients(Layer[] layers, int index) {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Layer {\n");
        sb.append("  inputSize: ").append(inputSize).append(",\n");
        sb.append("  outputSize: ").append(outputSize).append(",\n");
        sb.append("  weights: [\n");
        for (int i = 0; i < outputSize; i++) {
            sb.append("    [");
            for (int j = 0; j < inputSize; j++) {
                sb.append(String.format("%.4f", weights[i][j]));
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
        sb.append("],\n");
        sb.append("  biases: [");
        for (int i = 0; i < outputSize; i++) {
            sb.append(String.format("%.4f", biases[i]));
            if (i < outputSize - 1) {
                sb.append(", ");
            }
        }
        sb.append("],\n");
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
