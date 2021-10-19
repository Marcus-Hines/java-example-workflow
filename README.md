# java-example-app

This workflow can be used as an example for developers wanting to deploy Java apps using Github actions.

# The workflow:
This example workflow will:
   1. Create a k8's cluster on AKS 
   2. Build your applications image
   3. Create and push your image to ACR (Azure Container Registry 
   4. Pull your image from ACR, and deploy your application on AKS 

Workflows are defined in a `.yaml` files. The workflow file can be named anything, but it _must_ be inside a directory `./.github/workflows/`

# Using variables and secrets in your `.yaml` workflow
You can define secrets and variables by navigating to your github repository's `settings` page. You can define a new secrets variables by selecting "New Repository Secret"
Once you define your secrets, you can reference them with the syntax `${{ secrets.VARIABLE_NAME }}`

You can now pass these values as arguments to your workflow actions, without having to define them in plain text. To use this workflow, make sure to define the required secrets in your Github repository.

```
   steps:
    - uses: actions/checkout@v2
    - uses: Marcus-Hines/cosmosdb-create@main
    with:
        ACCOUNT_NAME: ${{ secrets.ACCOUNT_NAME }}
        RESOURCE_GROUP_NAME: ${{ secrets.RESOURCE_GROUP_NAME }}
        ARM_SUBSCRIPTION_ID: ${{ secrets.ARM_SUBSCRIPTION_ID }}
        ARM_TENANT_ID: ${{ secrets.ARM_TENANT_ID }}
        SP_USERNAME: ${{ secrets.SP_USERNAME }}
        SP_SECRET: ${{ secrets.SP_SECRET }}
        MSI_OBJECT_ID: ${{ secrets.MSI_OBJECT_ID }}
        MSI_NAME: ${{ secrets.MSI_NAME }}
```




