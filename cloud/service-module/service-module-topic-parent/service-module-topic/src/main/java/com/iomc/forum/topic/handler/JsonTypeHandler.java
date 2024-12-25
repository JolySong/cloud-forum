package com.iomc.forum.topic.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iomc.forum.topic.api.entity.Tag;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedTypes(List.class)
public class JsonTypeHandler extends BaseTypeHandler<List<Tag>> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Tag> parameter, JdbcType jdbcType)
            throws SQLException {
        try {
            ps.setString(i, MAPPER.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting value " + parameter, e);
        }
    }

    @Override
    public List<Tag> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parseJSON(rs.getString(columnName));
    }

    @Override
    public List<Tag> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJSON(rs.getString(columnIndex));
    }

    @Override
    public List<Tag> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJSON(cs.getString(columnIndex));
    }

    private List<Tag> parseJSON(String json) throws SQLException {
        try {
            if (json == null) {
                return null;
            }
            return MAPPER.readValue(json,
                    MAPPER.getTypeFactory().constructCollectionType(List.class, Tag.class));
        } catch (JsonProcessingException e) {
            throw new SQLException("Error parsing JSON " + json, e);
        }
    }
}