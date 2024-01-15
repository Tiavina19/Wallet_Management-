package Repository;

import java.util.List;

public interface CrudOperations<T> {

    // Create
    void create(T entity);

    // Read
    T findById(String id);
    List<T> findAll();

    // Update
    void update(T entity);

    // Delete
    void deleteById(String id);
    void delete(T entity);
}
