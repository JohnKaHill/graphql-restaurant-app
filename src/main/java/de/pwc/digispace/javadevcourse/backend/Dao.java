package de.pwc.digispace.javadevcourse.backend;

import java.util.Map;

public interface Dao<T, U> {
	
	void add(T t);
	void update(T t);
	boolean delete(U u);
	Map<U, T> findAll();
	T findById(U u);

}
