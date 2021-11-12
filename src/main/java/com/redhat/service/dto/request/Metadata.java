package com.redhat.service.dto.request;

import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {
    String name;
    String kafkaId = "Ignored";
    String owner;
    Date createdAt;
    Date updatedAt;
    Long resourceVersion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("kafka_id")
    public String getKafkaId() {
        return kafkaId;
    }

    @JsonProperty("kafka_id")
    public void setKafkaId(String kafkaId) {
        this.kafkaId = kafkaId;
    }

    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("resource_version")
    public Long getResourceVersion() {
        return resourceVersion;
    }

    @JsonProperty("resource_version")
    public void setResourceVersion(Long resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "name='" + name + '\'' +
                ", kafkaId='" + kafkaId + '\'' +
                ", owner='" + owner + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", resourceVersion=" + resourceVersion +
                '}';
    }
}
