package com.createcosmos.action.resource;

import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.azure.identity.*;
import com.azure.identity.implementation.IdentityClient;
import com.azure.identity.implementation.IdentityClientBuilder;


public class App {
    private final String DATABASE_NAME = "DemoDatabase";
    private final String CONTAINER_NAME = "CustomerDetail";
    //Cosmos Account Details
    private final String COSMOS_PATH = "documents.azure.com:443/";
    private final String COSMOS_ACCOUNT_NAME = "cosmosdbapp";

    private final String ENDPOINT = "https://" + COSMOS_ACCOUNT_NAME + "." + COSMOS_PATH;
    public final String MANAGED_IDENTITY_CLIENT_ID = "17b0320a-aa7d-4904-a899-db1f8cf46c5a";



    private CosmosContainer container;
    private CosmosDatabase database;
    private CosmosAsyncClient cosmosClient;

    public App(String databaseName) {
      // DefaultAzureCredential msi = createMSICredentials();
       ManagedIdentityCredential cred =  new ManagedIdentityCredentialBuilder().clientId(MANAGED_IDENTITY_CLIENT_ID).build();
       this.cosmosClient = new CosmosClientBuilder().endpoint(ENDPOINT).credential(cred).buildAsyncClient();

       // this.cosmosClient = createCosmosClient(msi);

       //this.database = cosmosClient.getDatabase(DATABASE_NAME);
       //this.container = database.getContainer(CONTAINER_NAME);
    }

    public void run() {
        System.out.println("App is starting...");
        this.cosmosClient.close();
    }

    private Customer getAllCustomers(){
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        CosmosPagedIterable<Customer> results = container.queryItems("SELECT * FROM c", queryOptions, Customer.class);

        for(Customer customer : results){
            System.out.println("Returning customer: " + customer.getFirstname() + " " + customer.getLastname());
        }
        return null;
    }

    private Customer getCustomer(String customerID){
        CosmosItemResponse<Customer> response = this.container.readItem(customerID, new PartitionKey("id"), Customer.class);
        Customer customer = response.getItem();

        System.out.println("Returning customer: " + customer.getFirstname() + " " + customer.getLastname());
        return customer;
    }

    private CosmosClient createCosmosClient(DefaultAzureCredential azureCredential){
        if(azureCredential == null){
            System.out.println("The given azureCredential object is null in createCosmosClient");
        }
        return new CosmosClientBuilder().credential(azureCredential).endpoint(ENDPOINT).buildClient();
    }

    private DefaultAzureCredential createMSICredentials(){
       // return new ManagedIdentityCredentialBuilder().clientId(MANAGED_IDENTITY_CLIENT_ID).build();

        DefaultAzureCredential defaultCredential = new DefaultAzureCredentialBuilder()
                .managedIdentityClientId(MANAGED_IDENTITY_CLIENT_ID)
                .build();

        return defaultCredential;
    }
}
