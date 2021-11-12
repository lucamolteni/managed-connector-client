package com.redhat.service;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.redhat.service.dto.request.Connector;
import com.redhat.service.dto.request.ConnectorResponseDTO;
import com.redhat.service.dto.request.ConnectorSpec;
import com.redhat.service.dto.request.ConnectorSpecKafka;
import com.redhat.service.dto.request.CreateConnectorRequest;
import com.redhat.service.dto.request.DeploymentLocation;
import com.redhat.service.dto.request.Kafka;
import com.redhat.service.dto.request.Metadata;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

public class ManagedConnectorServiceApplication {

    static String bearerToken;
    String baseUrl;
    String kafkaUrl;
    String serviceAccountId;
    String serviceAccountSecret;

    private static final Logger LOG = Logger.getLogger(ManagedConnectorServiceApplication.class);
    private String webhookUrl;

    // Used for HTTP Authentication
    public static String bearerToken() {
        return "Bearer " + bearerToken.trim();
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 6) {
            System.out.println("Usage: arg <BEARER_TOKEN> " +
                                       "<MC_BASE_URL> " +
                                       "<KAFKA_URL> " +
                                       "<SERVICE_ACCOUNT_ID> " +
                                       "<SERVICE_ACCOUNT_SECRET> " +
                                       "<SLACK_WEBHOOK_URL>");
            System.exit(1);
        }

        ManagedConnectorServiceApplication managedConnectorServiceApplication = new ManagedConnectorServiceApplication();
        managedConnectorServiceApplication.bearerToken = args[0];
        managedConnectorServiceApplication.baseUrl = args[1];
        managedConnectorServiceApplication.kafkaUrl = args[2];
        managedConnectorServiceApplication.serviceAccountId = args[3];
        managedConnectorServiceApplication.serviceAccountSecret = args[4];
        managedConnectorServiceApplication.webhookUrl = args[5];
        ConnectorResponseDTO slackConnector = managedConnectorServiceApplication.createSlackConnector();
        do {
            slackConnector = managedConnectorServiceApplication.pollSlackConnector(slackConnector);
        } while (!slackConnector.getStatus().equals("ready"));
    }

    private ConnectorResponseDTO pollSlackConnector(ConnectorResponseDTO connector) throws URISyntaxException, InterruptedException {
        URI apiUri = new URI(baseUrl);

        ManagedConnectorService reviewSvc = RestClientBuilder.newBuilder()
                .baseUri(apiUri)
                .build(ManagedConnectorService.class);

        ConnectorResponseDTO response = reviewSvc.connector(connector.getId());
        System.out.println(response.getStatus());
        Thread.sleep(5000);
        return response;
    }

    private ConnectorResponseDTO createSlackConnector() throws Exception {
        URI apiUri = new URI(baseUrl);

        ManagedConnectorService reviewSvc = RestClientBuilder.newBuilder()
                .baseUri(apiUri)
                .build(ManagedConnectorService.class);

        CreateConnectorRequest createConnectorRequest = new CreateConnectorRequest();

        Metadata metadata = new Metadata();
        metadata.setName("openbridge-slack-connector");
        createConnectorRequest.setMetadata(metadata);

        DeploymentLocation deploymentLocation = new DeploymentLocation();
        deploymentLocation.setKind("addon");
        deploymentLocation.setClusterId("c4ovtrsldcav5gaeqkn0");
        createConnectorRequest.setDeploymentLocation(deploymentLocation);

        createConnectorRequest.setConnectorTypeId("slack_sink_0.1");

        Kafka kafka = new Kafka();
        kafka.setBootstrapServer(kafkaUrl);
        kafka.setClientId(serviceAccountId);
        kafka.setClientSecret(serviceAccountSecret);
        createConnectorRequest.setKafka(kafka);

        ConnectorSpec connectorSpec = new ConnectorSpec();

        ConnectorSpecKafka connectorSpecKafka = new ConnectorSpecKafka();
        connectorSpecKafka.setTopic("slacktopic");
        connectorSpec.setConnectorSpecKafka(connectorSpecKafka);

        Connector connector = new Connector();
        connector.setChannel("mc");
        connector.setWebhookUrl(webhookUrl);
        connectorSpec.setConnector(connector);

        createConnectorRequest.setConnectorSpec(connectorSpec);

        try {
            ConnectorResponseDTO connectorResult = reviewSvc.createConnector(createConnectorRequest, true);
            System.out.println("Connector created: " + connectorResult);
            return connectorResult;
        } catch (WebApplicationException e) {
            Response response = e.getResponse();
            System.out.println("Error code: " + response.getStatus());

            ByteArrayInputStream arrayInputStream = (ByteArrayInputStream) response.getEntity();

            Scanner scanner = new Scanner(arrayInputStream);
            scanner.useDelimiter("\\Z");//To read all scanner content in one String
            String data = "";
            if (scanner.hasNext()) {
                data = scanner.next();
            }
            System.out.println(data);

            throw e;
        }
    }
}