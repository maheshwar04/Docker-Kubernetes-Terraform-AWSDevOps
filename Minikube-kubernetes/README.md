
# Hello Minikube: Kubernetes on Your Local Machine

This guide helps you run a simple sample application using Kubernetes and Minikube.

## Prerequisites

Make sure you have the following installed:

- [Kubectl](https://kubernetes.io/docs/tasks/tools/)
- [Minikube](https://minikube.sigs.k8s.io/docs/start/)

## 1. Start Minikube

```bash
minikube start
```
## 1. Minikube Dashboard
```bash
minikube dashboard
```
![Screenshot 2025-04-14 181937](https://github.com/user-attachments/assets/9f8201b5-6a29-459e-9e4a-de91b1b367ac)
![Screenshot 2025-04-14 181921](https://github.com/user-attachments/assets/07535469-f9a3-4775-a27d-a065d92d4584)


## 3. Deploy Hello Minikube App

Create a deployment:

```bash
kubectl create deployment hello-node --image=registry.k8s.io/e2e-test-images/agnhost:2.39 -- /agnhost netexec --http-port=8080

```

Expose the deployment:

```bash
kubectl expose deployment hello-node --type=LoadBalancer --port=8080
```

## 4. Access the App

Use `minikube service` to open the service in a browser:

![Screenshot 2025-04-14 182128](https://github.com/user-attachments/assets/2dcb29a1-5975-43ac-bbdb-773fde9a3a28)

```bash
minikube service hello-node
```

## 5. View Pod Logs

To view logs:

```bash
kubectl logs <your node>
```
![Screenshot 2025-04-14 182148](https://github.com/user-attachments/assets/dd1d17e6-0cb5-4189-aef4-997a731fcc66)

## 6. Clean Up

To delete the deployment and service:

```bash
kubectl delete service hello-minikube
kubectl delete deployment hello-minikube
```

To stop Minikube:

```bash
minikube stop
```
To delete Minikube:

```bash
minikube delete
```
![Screenshot 2025-04-14 182535](https://github.com/user-attachments/assets/c4eb8916-76e5-4ba7-9353-47235dc0768a)

## Resources

- Full tutorial: [Hello Minikube](https://kubernetes.io/docs/tutorials/hello-minikube/)
