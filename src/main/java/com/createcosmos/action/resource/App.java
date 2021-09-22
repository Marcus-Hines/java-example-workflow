package com.createcosmos.action.resource;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.identity.*;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
    private final String databaseName = "ToDoList";
    private final String containerName = "Items";
    private final String endpoint = "https://mahines-cosmosdb.documents.azure.com:443/";

    public final String MANAGED_IDENTITY_CLIENT_ID = "";
    public final String VAULT_URL = "";


    public App(String databaseName) {
        //this.databaseName = databaseName;
    }

    public void run() {
        System.out.println("App is starting...");
        //Managed Identity Credentials
        DefaultAzureCredential azureCredential = createDefaultAzureCredentialForUserAssignedManagedIdentity();

        //Cosmos DB client
        //CosmosClient cosmosClient = createCosmosClient(azureCredential);

       // CosmosDatabaseResponse response = cosmosClient.createDatabaseIfNotExists(this.databaseName);


       // System.out.println(response.getStatusCode());
    }

    private CosmosClient createCosmosClient(DefaultAzureCredential azureCredential){
        return new CosmosClientBuilder().credential(azureCredential).buildClient();
    }

    /**
     * The default credential will use the user assigned managed identity with the specified client ID.
     */
    public DefaultAzureCredential createDefaultAzureCredentialForUserAssignedManagedIdentity() {
        return new DefaultAzureCredentialBuilder()
                .managedIdentityClientId(MANAGED_IDENTITY_CLIENT_ID)
                .build();
    }

    private SecretClient createClientWithCredentials(DefaultAzureCredential defaultCredential){
        // Azure SDK client builders accept the credential as a parameter
        return new SecretClientBuilder()
                .vaultUrl(VAULT_URL)
                .credential(defaultCredential)
                .buildClient();
    }

    private List<Customer> generateApplicationData(){
        List<Customer> applicationData =  new ArrayList<Customer>();

        Customer OO7 = new Customer(1,"Bond","...Jame Bond");
        Customer scarface = new Customer(2,"Tony","Montana");
        Customer batman = new Customer(3,"Bruce","Wayne");

        applicationData.add(OO7);
        applicationData.add(scarface);
        applicationData.add(batman);

        return applicationData;
    }
}
/*
2021-09-20 13:06:04.175 ERROR 1 --- [
   main] c.azure.identity.EnvironmentCredential:
    Azure Identity => ERROR in EnvironmentCredential: Failed to determine an authentication scheme based on the available environment variables.
    Please specify AZURE_TENANT_ID and AZURE_CLIENT_SECRET to authenticate through a ClientSecretCredential;
    AZURE_TENANT_ID and AZURE_CLIENT_CERTIFICATE_PATH to authenticate through a ClientCertificateCredential;
    or AZURE_USERNAME and AZURE_PASSWORD to authenticate through a UserPasswordCredential.

 */
