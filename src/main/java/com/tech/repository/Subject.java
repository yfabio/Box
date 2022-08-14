package com.tech.repository;

public interface Subject {

	public void register(Observer observer);
	public void unregister(Observer observer);
	public void reportAll();
}
