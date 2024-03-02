package com.example.project.utils.observer;


import com.example.project.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}