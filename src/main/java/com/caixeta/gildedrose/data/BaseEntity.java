package com.caixeta.gildedrose.data;

import javax.persistence.*;
import java.util.Calendar;

@MappedSuperclass
public abstract class BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Calendar createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Calendar updatedAt;

    public BaseEntity() {
        super();
        this.createdAt = Calendar.getInstance();
    }

    @PrePersist
    protected final void updateTimeInfoBeforePersist() {
        this.createdAt = this.updatedAt = Calendar.getInstance();
    }

    @PreUpdate
    protected final void updateTimeInfoBeforeUpdate() {
        this.updatedAt = Calendar.getInstance();
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }
}