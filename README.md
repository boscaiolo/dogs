# Dog breeds manager

A sample Spring Boot App with Thymeleaf and Spring MVC which allows to track dog breeds and their respective sub breeds.


## Source code

https://github.com/boscaiolo/dogs

## Deployment

https://dogs.cfapps.io

The deployment uses cloud foundry through the manifest.yaml file.
The app uses mysql as data store and relies on mysql service created in the cloud foundry space where it is deployed.

To deploy in a cloud foundry space clone the repo and from the root folder

Create the my-sql service using:
```
cf create-service cleardb spark my-sql 
```
That will create a cleardb mysql service with a spark subscription which is free.

Then deploy using:
```
cf push 
```