package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.Arrays;

public enum StudyCafePassProvider implements StudyCafePassProvidable {
    HOURLY("1", "시간 단위 이용권") {
        @Override
        public StudyCafePassType provide(String optionNumber) {
            return StudyCafePassType.HOURLY;
        }
    },
    WEEKLY("2", "주 단위 이용권") {
        @Override
        public StudyCafePassType provide(String optionNumber) {
            return StudyCafePassType.WEEKLY;
        }
    },
    FIXED("3", "1인 고정석") {
        @Override
        public StudyCafePassType provide(String optionNumber) {
            return StudyCafePassType.FIXED;
        }
    },
    ;

    private final String optionNumber;
    private final String description;

    StudyCafePassProvider(String optionNumber, String description) {
        this.optionNumber = optionNumber;
        this.description = description;
    }

    public static StudyCafePassType findStudyCafePassTypeFrom(String optionNumber) {
        StudyCafePassProvider provider = findBy(optionNumber);

        return provider.provide(optionNumber);
    }

    private static StudyCafePassProvider findBy(String optionNumber) {
        return Arrays.stream(values())
                .filter(provider -> provider.supports(optionNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 옵션입니다."));
    }

    @Override
    public boolean supports(String optionNumber) {
        return this.optionNumber.equals(optionNumber);
    }
}
