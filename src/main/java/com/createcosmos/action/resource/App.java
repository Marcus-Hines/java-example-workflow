package com.createcosmos.action.resource;

import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosItemResponse;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.azure.identity.*;


public class App {
    private final String DATABASE_NAME = "DemoDatabase";
    private final String CONTAINER_NAME = "CustomerDetails";
    //Cosmos Account Details
    private final String COSMOS_PATH = "documents.azure.com:443/";
    private final String COSMOS_ACCOUNT_NAME = "cosmosdbapp";

    private final String ENDPOINT = "https://" + COSMOS_ACCOUNT_NAME + "." + COSMOS_PATH;
    public final String MANAGED_IDENTITY_CLIENT_ID = "5bca3991-8179-4edc-86b4-fb8a145d6f6d";

    private CosmosContainer container;
    private CosmosDatabase database;
    private CosmosClient cosmosClient;

    public App(String databaseName) {


        //Managed Identity Credentials
       // ManagedIdentityCredential msi = createMSICredentials();
        DefaultAzureCredential credential =  new DefaultAzureCredentialBuilder().build();
       this.cosmosClient = createCosmosClient(credential);

       //this.database = cosmosClient.getDatabase(DATABASE_NAME);
       //this.container = database.getContainer(CONTAINER_NAME);
    }

    public void run() {
        System.out.println("App is starting...");
      //  this.getAllCustomers();
        DefaultAzureCredential credential =  new DefaultAzureCredentialBuilder().build();
        CosmosClient client = new CosmosClientBuilder().credential(credential).buildClient();
        client.close();

       // this.cosmosClient.close();
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

    private ManagedIdentityCredential createMSICredentials(){
        return new ManagedIdentityCredentialBuilder().clientId(MANAGED_IDENTITY_CLIENT_ID).build();
    }
}
