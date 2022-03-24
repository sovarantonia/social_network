package repository;

import java.util.ArrayList;

public interface Repository <T, E>{
    void add(T e);
    void remove(T e);
    T getObject(E field);
    ArrayList<T> getAll();
}
