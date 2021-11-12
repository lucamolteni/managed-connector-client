package com.redhat.service.dto.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectorResponseDTO {

    @JsonProperty("id")
    String id;

    @JsonProperty("kind")
    String kind = "Connector";

    @JsonProperty("connector_type_id")
    String connectorTypeId;

    @JsonProperty("metadata")
    Metadata metadata = new Metadata();

    @JsonProperty("deployment_location")
    DeploymentLocation deploymentLocation = new DeploymentLocation();


    @JsonProperty("desired_state")
    String desiredState;

    @JsonProperty("status")
    String status;

    @JsonProperty("connector_spec")
    private ConnectorSpec connectorSpec;

    /**
     * Managed Kafka Source
     * <p>
     *
     *
     */
    @JsonProperty("kafka")
    private Kafka kafka;
    @JsonProperty("steps")
    private List<Object> steps = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Managed Kafka Source
     * <p>
     *
     *
     */
    @JsonProperty("kafka")
    public Kafka getKafka() {
        return kafka;
    }

    /**
     * Managed Kafka Source
     * <p>
     *
     *
     */
    @JsonProperty("kafka")
    public void setKafka(Kafka kafka) {
        this.kafka = kafka;
    }

    @JsonProperty("steps")
    public List<Object> getSteps() {
        return steps;
    }

    @JsonProperty("steps")
    public void setSteps(List<Object> steps) {
        this.steps = steps;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonProperty("kind")
    public String getKind() {
        return kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @JsonProperty("deployment_location")
    public DeploymentLocation getDeploymentLocation() {
        return deploymentLocation;
    }

    @JsonProperty("deployment_location")
    public void setDeploymentLocation(DeploymentLocation deploymentLocation) {
        this.deploymentLocation = deploymentLocation;
    }

    @JsonProperty("connector_type_id")
    public String getConnectorTypeId() {
        return connectorTypeId;
    }

    @JsonProperty("connector_type_id")
    public void setConnectorTypeId(String connectorTypeId) {
        this.connectorTypeId = connectorTypeId;
    }

    @JsonProperty("connector_spec")
    public ConnectorSpec getConnectorSpec() {
        return connectorSpec;
    }

    @JsonProperty("connector_spec")
    public void setConnectorSpec(ConnectorSpec connectorSpec) {
        this.connectorSpec = connectorSpec;
    }

    @JsonProperty("desired_state")
    public String getDesiredState() {
        return desiredState;
    }

    @JsonProperty("desired_state")
    public void setDesiredState(String desiredState) {
        this.desiredState = desiredState;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ConnectorResponseDTO{" +
                "id='" + id + '\'' +
                ", kind='" + kind + '\'' +
                ", connectorTypeId='" + connectorTypeId + '\'' +
                ", metadata=" + metadata +
                ", deploymentLocation=" + deploymentLocation +
                ", desiredState='" + desiredState + '\'' +
                ", status='" + status + '\'' +
                ", connectorSpec=" + connectorSpec +
                ", kafka=" + kafka +
                ", steps=" + steps +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
