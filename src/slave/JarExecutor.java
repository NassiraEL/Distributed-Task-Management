package slave;

import shared.TaskMessage;
import strategy.StrategyFactory;
import strategy.TaskProcessingStrategy;
import java.io.File;
import java.nio.file.Files;

public class JarExecutor {
    public byte[] execute(TaskMessage msg) throws Exception {
        // 1. Créer un dossier temporaire pour le Slave
        File dir = new File("slaveTask_" + System.currentTimeMillis());
        dir.mkdirs();

        // 2. Enregistrer le Slave JAR envoyé par l'User
        File jarFile = new File(dir, msg.getSlaveJarName());
        Files.write(jarFile.toPath(), msg.getSlaveJarContent());

        // 3. Enregistrer le fichier de données (l'image ou autre)
        File inputFile = new File(dir, msg.getFileName());
        Files.write(inputFile.toPath(), msg.getFileContent());

        // 4. Appeler la strategy en lui passant le JAR et le fichier
        TaskProcessingStrategy strategy = StrategyFactory.getStrategy(msg.getSlaveOptions());

        return strategy.process(jarFile, inputFile, msg.getSlaveOptions());
    }
}