package org.edu.ntnu.idatt2003.group49.millions.model;

import org.edu.ntnu.idatt2003.group49.millions.view.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
  List<Observer> observers;

  public Subject() {
    observers = new ArrayList<>();
  }

  public void addObserver(Observer observer) {
    observers.add(observer);
  };

  public void removeObserver(Observer observer) {
    observers.remove(observer);
  };

  public abstract void notifyObservers();
}
