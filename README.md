# cosmosdb-java-example-app

This workflow can be used by apps that will be using a managed cosmosdb instance on AKS, and want their application to communicate with cosmosdb using MSI (Managed Service Identity)

# Before using this workflow:
You will need to create an MSI in aks that will be assigned to the nodes on your AKS cluster. This MSI will be granted the permissions required to access the cosmosdb instance that is created by this action
The following inputs are required:

`ACCOUNT_NAME:` (_required_) This is whatever you would like for you cosmosdb account in AKS to be named
. __Example:__. `mycosmosdb`

`RESOURCE_GROUP_NAME:` (_required_) This is the resource group that you want your cosmosdb account to be created in.
. __Example:__. `cosmosrg`

`SUBSCRIPTION_ID:` (_required_) This is the subscription id that your resource group belongs to
. __Example:__. `00000000-0000-0000-0000-000000000000`


`OBJECT_ID:` (_required_) This is the object id or principle id associated with the MSI that you will be using to communicate with CosmosDB
. __Example:__. `00000000-0000-0000-0000-000000000000` **If you don't have an MSI, you can run:

     az identity create -g < your-resource-group> -n <whatever-you-want-to-name-your-msi>
     echo $(az identity show -n <whatever-you-want-to-name-your-msi> -g <your-resource-group> --query principalId --out tsv)

This will create the MSI and output the object id (principle id) of the MSI. You can then copy the object id and pass that as an arg to this action, and also reference it in your application code when attempting to access
CosmosDB with MSI.



