package org.yang.zhang.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 08 31 17:54
 */

public enum UserStatusType implements BaseEnum{

    ONLINE(0,"在线"),
    BUSY(1,"请勿打扰"),
    INVISIBLE(2,"隐身"),
    OFFLINE(3,"下线");

    private Integer value;
    private String text;

    UserStatusType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    @JsonCreator
    public static UserStatusType parseValue(Integer value) {
        if (value == null) {
            return null;
        }
        return Stream.of(values())
                .filter(e -> e.getValue() == value)
                .findFirst().orElse(null);
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
