package com.redhat.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.redhat.service.dto.request.ConnectorResponseDTO;
import com.redhat.service.dto.request.CreateConnectorRequest;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

@Path("/api/connector_mgmt/v1")
public interface ManagedConnectorService {

    @GET
    @Path("kafka_connector_types")
    @ClientHeaderParam(name="Authorization", value="{com.redhat.service.ManagedConnectorServiceApplication.bearerToken}")
    String allConnectorTypes();

    @GET
    @Path("kafka_connectors/{id}")
    @ClientHeaderParam(name="Authorization", value="{com.redhat.service.ManagedConnectorServiceApplication.bearerToken}")
    ConnectorResponseDTO connector(@PathParam("id") String connectorId);

    @POST
    @Path("kafka_connectors")
    @ClientHeaderParam(name="Authorization", value="{com.redhat.service.ManagedConnectorServiceApplication.bearerToken}")
    ConnectorResponseDTO createConnector(CreateConnectorRequest createConnectorRequest, @QueryParam("async") Boolean async);
}