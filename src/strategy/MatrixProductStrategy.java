package strategy;

import java.io.File;
import java.nio.file.Files;

public class MatrixProductStrategy implements TaskProcessingStrategy {

    @Override
    public byte[] process(File jarFile, File inputFile, String option) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
                "java", "-jar", jarFile.getName(), option, inputFile.getName());

        pb.directory(jarFile.getParentFile());

        Process process = pb.start();
        process.waitFor();

        File resultFile = new File(inputFile.getParent(), "Result.txt");

        if (!resultFile.exists()) {
            throw new Exception("Problem: The JAR did not generate the Result.txt file!");
        }

        return Files.readAllBytes(resultFile.toPath());
    }
}