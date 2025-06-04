package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafePassType;

public interface StudyCafePassProvidable {

    boolean supports(String optionNumber);

    StudyCafePassType provide(String optionNumber);

}
