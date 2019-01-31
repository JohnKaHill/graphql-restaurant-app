package de.pwc.digispace.javadevcourse.backend;

import java.util.List;

public interface Dao<T, U> {
	
	void add(T t);
	void update(T t);
	boolean delete(U u);
	List<T> findAll();
	T findById(U u);

}
