package strategy;

public class StrategyFactory {

    public static TaskProcessingStrategy getStrategy(String taskType) {
        switch (taskType.toLowerCase()) {
            case "gray":
                return new GrayFilterStrategy();
            case "product":
                return new MatrixProductStrategy();
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }
}