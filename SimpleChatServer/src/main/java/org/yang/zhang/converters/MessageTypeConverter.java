package org.yang.zhang.converters;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.yang.zhang.enums.MessageType;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 27 11:45
 */
@MappedJdbcTypes({JdbcType.TINYINT})
@MappedTypes({MessageType.class})
@Converter(autoApply = true)
public class MessageTypeConverter implements AttributeConverter<MessageType, Integer>, TypeHandler<MessageType> {
    @Override
    public Integer convertToDatabaseColumn(MessageType enumItem) {
        if (enumItem == null) {
            return null;
        }
        return enumItem.getValue();
    }

    @Override
    public MessageType convertToEntityAttribute(Integer value) {
        return MessageType.parseValue(value);
    }

    @Override
    public MessageType getResult(ResultSet rs, String param) throws SQLException {
        if (rs.getObject(param) == null) {
            return null;
        }
        return MessageType.parseValue(rs.getInt(param));
    }

    @Override
    public MessageType getResult(ResultSet rs, int col) throws SQLException {
        if (rs.getObject(col) == null) {
            return null;
        }
        return MessageType.parseValue(rs.getInt(col));
    }

    @Override
    public MessageType getResult(CallableStatement cs, int col) throws SQLException {
        if (cs.getObject(col) == null) {
            return null;
        }
        return MessageType.parseValue(cs.getInt(col));
    }

    @Override
    public void setParameter(PreparedStatement ps, int paramInt, MessageType enumItem, JdbcType jdbctype)
            throws SQLException {
        if (enumItem == null) {
            ps.setObject(paramInt, null);
            return;
        }
        ps.setInt(paramInt, enumItem.getValue());
    }
}
