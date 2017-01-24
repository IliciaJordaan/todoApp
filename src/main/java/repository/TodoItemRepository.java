package repository;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import models.TodoItem;

import javax.persistence.EntityManager;
import java.util.List;

@Singleton
public class TodoItemRepository {

    @Inject
    private Provider<EntityManager> entityManagerProvider;

    private EntityManager getEntityManager() {
        return entityManagerProvider.get();
    }

    @Transactional
    public List<TodoItem> listAll() {
        return getEntityManager()
                .createQuery("SELECT items FROM TodoItem items")
                .getResultList();
    }

    @Transactional
    public TodoItem getById(Long todoItemId) {
        return (TodoItem) getEntityManager()
                .createQuery("SELECT item FROM TodoItem item WHERE item.id = :id")
                .setParameter("id", todoItemId)
                .getSingleResult();
    }

    @Transactional
    public void persist(TodoItem entity){
        getEntityManager().persist(entity);
    }

    @Transactional
    public void update(TodoItem entity){
        getEntityManager().merge(entity);
    }

    @Transactional
    public void delete(Long todoItemId) {
        getEntityManager()
                .createQuery("DELETE FROM TodoItem WHERE id = :id")
                .setParameter("id", todoItemId)
                .executeUpdate();
    }
}
