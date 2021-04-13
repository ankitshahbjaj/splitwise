package com.setu.splitwise.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
@Data
public class BaseEntity {
    @Column(name = "last_updated_time", nullable = false)
    private Long lastUpdatedTime;

    @Column(name = "created_time", nullable = false)
    private Long createdTime;

    @Column(name = "created_by", nullable = false)
    protected String createdBy;

    @Column(name = "last_updated_by", nullable = false)
    protected String lastUpdatedBy;

    @Version
    private Integer version;

    protected BaseEntity() {
        if (createdTime == null) {
            createdTime = System.currentTimeMillis();
        }
        lastUpdatedTime = System.currentTimeMillis();
    }
}
