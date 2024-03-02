package com.example.project.entities;

import java.util.Objects;

public class Entity<ID>{
    private ID id;

    /**
     * Returns id of current entity
     * @return ID, id of current entity
     */
    public ID getId() {
        return id;
    }

    /**
     * Sets id of current entity
     * @param id, ID, new id
     */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity that = (Entity) o;
        return id.equals(that.id); /*&& departure.equals(that.departure) && destination.equals(that.destination)*/
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
