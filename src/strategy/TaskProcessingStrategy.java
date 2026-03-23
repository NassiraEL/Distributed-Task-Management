package strategy;

import java.io.File;

public interface TaskProcessingStrategy {
    byte[] process(File jarFile, File inputFile, String option) throws Exception;
}