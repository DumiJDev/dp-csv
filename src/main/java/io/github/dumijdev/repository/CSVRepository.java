package io.github.dumijdev.repository;

import java.util.List;

public interface CSVRepository<T> {
    List<T> findAll();
    List<T> findAll(Page page);
    T findBy(String column, Object value);
}
