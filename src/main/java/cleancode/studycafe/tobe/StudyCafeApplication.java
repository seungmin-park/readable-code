package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.config.StudyCafeConfig;
import cleancode.studycafe.tobe.io.ConsoleInputHandler;
import cleancode.studycafe.tobe.io.ConsoleOutputHandler;
import cleancode.studycafe.tobe.io.reader.CsvStudyCafeDataReader;

public class StudyCafeApplication {

    public static void main(String[] args) {
        StudyCafeConfig config = new StudyCafeConfig(new CsvStudyCafeDataReader(), new ConsoleInputHandler(), new ConsoleOutputHandler());
        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(config);

        studyCafePassMachine.run();
    }

}
