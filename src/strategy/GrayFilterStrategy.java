package strategy;

import java.io.File;
import java.nio.file.Files;

public class GrayFilterStrategy implements TaskProcessingStrategy {

    @Override
    public byte[] process(File jarFile, File inputFile, String option) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(
                "java", "-jar", jarFile.getName(), option, inputFile.getName());

        pb.directory(jarFile.getParentFile());
        pb.start().waitFor();

        File resultFile = new File(inputFile.getParent(), "Result.jpg");
        return Files.readAllBytes(resultFile.toPath());
    }
}
