package io.github.dumijdev.processor;

import io.github.dumijdev.exception.BadDefinitionCSVColumnException;
import io.github.dumijdev.exception.NoCSVEntityException;
import io.github.dumijdev.models.CSVLine;
import io.github.dumijdev.stereotype.CSVColumn;
import io.github.dumijdev.stereotype.CSVEntity;
import io.github.dumijdev.stereotype.CSVPath;

import java.lang.reflect.InvocationTargetException;

public class DefaultAdapter implements Adapter {
    @Override
    public <T> T adapt(CSVLine line, Class<T> type) {
        try {
            var instance = type.getDeclaredConstructor().newInstance();

            if (!type.isAnnotationPresent(CSVEntity.class)) {
                throw new NoCSVEntityException("A classe não está marcada com a anotação @CSVEntity");
            }

            var hasHeader = type.getAnnotation(CSVPath.class).hasHeader();

            for (var field : type.getDeclaredFields()) {
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
        } else if (targetType.equals(Double.class) || targetType.equals(double.class)) {
            return Double.parseDouble(column.getValue());
        } else if (targetType.equals(Boolean.class) || targetType.equals(boolean.class)) {
            return Boolean.parseBoolean(column.getValue());
        } else {
            throw new UnsupportedOperationException("Conversão para o tipo " + targetType + " não suportada.");
        }
    }
}
