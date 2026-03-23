package shared;

import java.io.Serializable;

public class TaskMessage implements Serializable {

    // Master Jar
    private String masterJarName;
    private byte[] masterJarContent;
    private String masterOptions;

    // Slave Jar
    private String slaveJarName;
    private byte[] slaveJarContent;
    private String slaveOptions;

    // File
    private String fileName;
    private byte[] fileContent;

    private byte[] resultFile;

    public TaskMessage(String masterJarName, byte[] masterJarContent,
            String masterOptions,
            String slaveJarName, byte[] slaveJarContent,
            String slaveOptions,
            String fileName, byte[] fileContent) {

        this.masterJarName = masterJarName;
        this.masterJarContent = masterJarContent;
        this.masterOptions = masterOptions;

        this.slaveJarName = slaveJarName;
        this.slaveJarContent = slaveJarContent;
        this.slaveOptions = slaveOptions;

        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    // Getters

    public String getMasterJarName() {
        return masterJarName;
    }

    public byte[] getMasterJarContent() {
        return masterJarContent;
    }

    public String getMasterOptions() {
        return masterOptions;
    }

    public String getSlaveJarName() {
        return slaveJarName;
    }

    public byte[] getSlaveJarContent() {
        return slaveJarContent;
    }

    public String getSlaveOptions() {
        return slaveOptions;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public byte[] getResultFile() {
        return resultFile;
    }

    public void setResultFile(byte[] resultFile) {
        this.resultFile = resultFile;
    }
}