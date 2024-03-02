package com.example.project.utils.events;


public class ChangeEvent<E> implements Event {
    private ChangeEventType type;
    private E data, oldData;

    public ChangeEvent(ChangeEventType type, E oldData) {
        this.type = type;
        this.oldData = oldData;
    }
    public ChangeEvent(ChangeEventType type, E data, E oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public E getData() {
        return data;
    }

    public E getOldData() {
        return oldData;
    }
}