package org.yang.zhang.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 27 11:48
 */

public interface BaseEnum {

    @JsonValue
    int getValue();

    static <E extends Enum<E> & BaseEnum> E fromValue(Class<E> cls, Integer value) {
        return Stream.of(cls.getEnumConstants())
                .filter(e -> e.getValue() == value)
                .findFirst().orElse(null);
    }
}
