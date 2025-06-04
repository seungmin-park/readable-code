package cleancode.studycafe.tobe.config;

import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.reader.StudyCafeDataReader;

public class StudyCafeConfig {

    private final StudyCafeDataReader studyCafeDataReader;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public StudyCafeConfig(StudyCafeDataReader studyCafeDataReader, InputHandler inputHandler, OutputHandler outputHandler) {
        this.studyCafeDataReader = studyCafeDataReader;
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public StudyCafeDataReader getStudyCafeDataReader() {
        return studyCafeDataReader;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }
}
