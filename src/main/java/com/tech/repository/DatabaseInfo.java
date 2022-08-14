package com.tech.repository;

import java.util.LinkedHashSet;
import java.util.Set;

public class DatabaseInfo implements Subject {
	
	private Set<Observer> observers = new LinkedHashSet<Observer>();
	
	private boolean tough;
			
	private static DatabaseInfo instance;
	
	public DatabaseInfo() {}
	
	public static DatabaseInfo getInstance() {
		if(instance==null) {
			instance = new DatabaseInfo();
		}
		return instance;
	}

	@Override
	public void register(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void reportAll() {
		observers.forEach(e -> e.update(this));
	}

	public boolean isTough() {
		return tough;
	}

	public void setTough(boolean tough) {
		this.tough = tough;
		reportAll();
	}

}
