## Hash Code Assignment

The system is composed of two services. One in scala (Discounts) and one in javascript (Products). A simple initial database is provided so the projects can easily run.

- Products - A service that can communicate through a rest api to return a list of stored products to the client. In case a user id is provided, it will fetch possible discounts from the Discounts service through a stream. 

- Discounts - A service that transforms a stream of requests (product id and user id) into discounts according to configured rules. 
 

## Requirements

To run the two services you will need Docker Compose. 

- Docker Compose - (https://docs.docker.com/compose/)

## Run

Just run `sh ./run.sh`. Both services and the database will be build and started.

## Development, Deployment

To develop and deploy look inside each service folder.

## Design

Both services were created so that they could resemble each other but not lose the features of each language. The projects structures are intentionally similar so the developer has less cognitive load when switching between them while trying to maintain as idiomatic as possible.

## Improvements

### Deploy images to a cluster (Kubernetes)

Right now each service builds an image and stores it on Google Cloud using Google Cloud Builder. The next step would be to make those images available and running in a cluster. A good solution would be to create kubernetes pods that can be deployed in any kubernetes cluster and add this step to the Cloud Build job, so that we can achieve continuous integration/delivery and service scalability through Horizontal and Vertical Pod Scalers.

### Health Check

It is important to be able to check for service health. Specially in a clustered environment, the underlying cluster manager must know it a pod is stable and healthy or not so it can redirect traffic and spawn new service instances. 

This can be achieved by using the `grpc-health-probe` and adding the **GRPC Health Checking Protocol** to each service then configuring readiness and liveness checks on kubernetes pod.

### Retries

In a clustered environment instances of the services can be created or destroyed without noticed, so it is extremely relevant for services to be able to handle retries. A good way of doing it would be to delegate the retry responsibility to a service mesh like Istio (https://istio.io/).

### Blue/Green Deployment or Canary Releases

To make sure releases don't have a bad impact in any environment, it is advised to have a form of deployment that can be gradually checked for correctness. This can be achieved by using an application like Spinnaker (https://www.spinnaker.io/) or manually with some help from a service mesh like Istio (https://istio.io/).

### Shared Protos

Depending on the amount of services and shared .proto files that arise from the evolution of the system, would be a great improvement to have a consistent way to share the .proto files. The actual implementation would heavily depend on how the development of the system evolves.
