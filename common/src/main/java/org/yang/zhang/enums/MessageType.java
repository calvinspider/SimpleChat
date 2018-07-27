package org.yang.zhang.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 15:51
 */

public enum  MessageType implements BaseEnum {
 REGISTER(1,"注册"),
 UNREGISTER(2,"注销"),
 ROOM(3,"聊天室"),
 NORMAL(4,"普通");

    private Integer value;
    private String text;

    MessageType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    @JsonCreator
    public static MessageType parseValue(Integer value) {
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
