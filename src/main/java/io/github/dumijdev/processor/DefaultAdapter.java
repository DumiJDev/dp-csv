package io.github.dumijdev.processor;

import io.github.dumijdev.exception.BadDefinitionCSVColumnException;
import io.github.dumijdev.exception.NoCSVEntityException;
import io.github.dumijdev.models.CSVLine;
import io.github.dumijdev.stereotype.CSVColumn;
import io.github.dumijdev.stereotype.CSVColumnIgnore;
import io.github.dumijdev.stereotype.CSVEntity;
import io.github.dumijdev.stereotype.CSVPath;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public class DefaultAdapter implements Adapter {
    @Override
    public <T> T adapt(CSVLine line, Class<T> type) {
        try {
            var instance = type.getDeclaredConstructor().newInstance();

            if (!type.isAnnotationPresent(CSVEntity.class)) {
                throw new NoCSVEntityException("A classe não está marcada com a anotação @CSVEntity");
            }

            boolean hasHeader = false;

            if (type.isAnnotationPresent(CSVPath.class))
                hasHeader = type.getAnnotation(CSVPath.class).hasHeader();

            for (var field : type.getDeclaredFields()) {

                if (field.isAnnotationPresent(CSVColumnIgnore.class)) {
                    continue;
                }

                if (field.isAnnotationPresent(CSVColumn.class)) {
                    var columnMetadata = field.getAnnotation(CSVColumn.class);
                    field.setAccessible(true);

                    if (!columnMetadata.name().isEmpty() && columnMetadata.position() != -1) {
                        throw new BadDefinitionCSVColumnException("Não se pode definir posição e nome ao mesmo tempo");
                    }

                    Object value;
                    if (hasHeader && !columnMetadata.name().isEmpty()) {
                        value = convertValue(line.getColumnByName(columnMetadata.name().toLowerCase()), field.getType());
                    } else if (columnMetadata.position() != -1) {
                        value = convertValue(line.getColumnByPosition(columnMetadata.position()), field.getType());
                    } else {
                        value = convertValue(line.getColumnByName(field.getName().toLowerCase()), field.getType());
                    }
                    field.set(instance, value);
                } else {
                    field.setAccessible(true);

                    Object value = convertValue(line.getColumnByName(field.getName().toLowerCase()), field.getType());
                    field.set(instance, value);
                }
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            // Lidar com as exceções apropriadamente ou lançar exceções específicas
            throw new RuntimeException("Erro ao adaptar linha para objeto", e);
        }
    }

    private Object convertValue(io.github.dumijdev.models.CSVColumn column, Class<?> targetType) {
        if (targetType.equals(String.class)) {
            return column.getValue();
        } else if (targetType.equals(Integer.class) || targetType.equals(int.class)) {
            return Integer.parseInt(column.getValue());
        } else if (targetType.equals(Long.class) || targetType.equals(long.class)) {
            return Long.parseLong(column.getValue());
        } else if (targetType.equals(Double.class) || targetType.equals(double.class)) {
            return Double.parseDouble(column.getValue());
        } else if (targetType.equals(Float.class) || targetType.equals(float.class)) {
            return Float.parseFloat(column.getValue());
        } else if (targetType.equals(Short.class) || targetType.equals(short.class)) {
            return Short.parseShort(column.getValue());
        } else if (targetType.equals(Byte.class) || targetType.equals(byte.class)) {
            return Byte.parseByte(column.getValue());
        } else if (targetType.equals(Character.class) || targetType.equals(char.class)) {
            if (column.getValue().length() == 1) {
                return column.getValue().charAt(0);
            } else {
                throw new IllegalArgumentException("A string de entrada não é um caractere válido para conversão.");
            }
        } else if (targetType.equals(Boolean.class) || targetType.equals(boolean.class)) {
            return Boolean.parseBoolean(column.getValue());
        } else if (targetType.equals(LocalDateTime.class)) {

            if (column.getValue().matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(column.getValue(), formatter).atStartOfDay();
            }

            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd[ HH[:mm[:ss[.S][.SS][.SSS]]]]")
                    .toFormatter();

            return LocalDateTime.parse(column.getValue(), formatter);
        } else if (targetType.equals(Date.class)) {
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(column.getValue(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS"));
                return java.sql.Timestamp.valueOf(localDateTime);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter para Date: " + e.getMessage(), e);
            }
        } else if (targetType.equals(LocalDate.class)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(column.getValue(), formatter);
        } else {
            return null;
            //throw new UnsupportedOperationException("Conversão para o tipo " + targetType + " não suportada.");
        }
    }
}
