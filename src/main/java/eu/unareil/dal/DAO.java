package eu.unareil.dal;

import java.util.List;

public interface DAO<T> {
    public long insert(T data) throws DALException;

    public void delete(T data) throws DALException;

    public void update(T data) throws DALException;

    public T selectById(long id) throws DALException;

    public List<T> selectAll() throws DALException;
}
