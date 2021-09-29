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

    private final String ENDPOINT = "https://mahinescosmos.documents.azure.com:443/";
    public final String MANAGED_IDENTITY_CLIENT_ID = "5bca3991-8179-4edc-86b4-fb8a145d6f6d";

    private CosmosContainer container;
    private CosmosDatabase database;
    private CosmosClient cosmosClient;

    public App(String databaseName) {
        //Managed Identity Credentials
        ManagedIdentityCredential msi = createMSICredentials();

        this.cosmosClient = createCosmosClient(msi);
        this.database = cosmosClient.getDatabase(DATABASE_NAME);
        this.container = database.getContainer(CONTAINER_NAME);
    }

    public void run() {
        System.out.println("App is starting...");
        this.getAllCustomers();
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

    private CosmosClient createCosmosClient(ManagedIdentityCredential azureCredential){
        if(azureCredential == null){
            System.out.println("The given azureCredential object is null in createCosmosClient");
        }
        return new CosmosClientBuilder().credential(azureCredential).endpoint(ENDPOINT).buildClient();
    }

    private ManagedIdentityCredential createMSICredentials(){
        return new ManagedIdentityCredentialBuilder().clientId(MANAGED_IDENTITY_CLIENT_ID).build();
    }

/*
    // Database Create
    private void createDatabaseIfNotExists() throws Exception {
        //logger.info("Create database " + databaseName + " if not exists...");

        //  Create database if not exists
        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists(databaseName);
        database = client.getDatabase(databaseResponse.getProperties().getId());

        logger.info("Done.");
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



    private void createDocument() throws Exception {
        logger.info("Create document " + documentId);

        // Define a document as a POJO (internally this
        // is converted to JSON via custom serialization)
        Family family = new Family();
        family.setLastName(documentLastName);
        family.setId(documentId);

        // Insert this item as a document
        // Explicitly specifying the /pk value improves performance.
        container.createItem(family,new PartitionKey(family.getLastName()),new CosmosItemRequestOptions());

        logger.info("Done.");
    }

    private void queryAllDocuments() throws Exception {
        logger.info("Query all documents.");

        executeQueryPrintSingleResult("SELECT * FROM c");
    }
*/
}
