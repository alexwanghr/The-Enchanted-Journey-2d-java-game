package com.journaldev.design.observer;

/*
// Tutorial //
Observer Design Pattern in Java
https://www.digitalocean.com/community/tutorials/observer-design-pattern-in-java
*/

public interface Subject {

    //methods to register and unregister observers
    public void register(Observer obj);

    public void unregister(Observer obj);

    //method to notify observers of change
    public void notifyObservers();

    //method to get updates from subject
    public EventType getUpdate(Observer obj);
}

