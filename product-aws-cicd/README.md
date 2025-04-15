# 🚀 CI/CD Pipeline with AWS CodePipeline, CodeBuild, ECR, ECS, and GitHub

This repository demonstrates a complete CI/CD workflow using:
- **GitHub** for source control
- **AWS CodePipeline** to orchestrate the process
- **AWS CodeBuild** to build Docker images
- **Amazon ECR** to store Docker images
- **Amazon ECS (Fargate)** to deploy the application
---

## 📦 Architecture Overview

```
GitHub → CodePipeline → CodeBuild → ECR → ECS (Fargate)
```
![Screenshot 2025-04-14 141630](https://github.com/user-attachments/assets/50fa2bdb-436a-4f2f-8e1c-18294397ab0d)

---

## 🛠 Prerequisites

- AWS account
- IAM role with permissions for ECS, ECR, CodeBuild, and CodePipeline
- ECS cluster and Fargate service created
- GitHub repository with:
  - `Dockerfile`
  - `buildspec.yml`
  - Application source code

---

## 📁 Project Structure

```
.
├── app/
│   └── (your application code)
├── Dockerfile
└── buildspec.yml
```

---

## 📄 Sample `buildspec.yml`

```yaml
version: 0.2

phases:
  pre_build:
    commands:
      - mvn clean install
      - echo Logging in to Amazon ECR...
      - aws --version
      - REPOSITORY_URI=442426873568.dkr.ecr.us-east-1.amazonaws.com/productservice
      - aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $REPOSITORY_URI
      - COMMIT_HASH=$(echo $CODEBUILD_RESOLVED_SOURCE_VERSION | cut -c 1-7)
      - IMAGE_TAG=build-$(echo $CODEBUILD_BUILD_ID | awk -F":" '{print $2}')
  build:
    commands:
      - echo Build started on `date`
      - echo Building the Docker image...
      - docker build -t $REPOSITORY_URI:latest .
      - docker tag $REPOSITORY_URI:latest $REPOSITORY_URI:$IMAGE_TAG
  post_build:
    commands:
      - echo Build completed on `date`
      - echo Pushing the Docker images...
      - docker push $REPOSITORY_URI:latest
      - docker push $REPOSITORY_URI:$IMAGE_TAG
      - echo Writing image definitions file...
      - printf '[{"name":"productservice","imageUri":"%s"}]' $REPOSITORY_URI:$IMAGE_TAG > imagedefinitions.json
      - echo printing imagedefinitions.json
      - cat imagedefinitions.json

artifacts:
  files:
    - imagedefinitions.json
    - target/product-0.0.1-SNAPSHOT.war
```

---

## 🔧 Setup Instructions

1. **Create an ECR Repository**
   ```bash
   aws ecr create-repository --repository-name your-app-repo
    ```
![Screenshot 2025-04-14 175235](https://github.com/user-attachments/assets/be1fc3cb-0bae-40d5-8df5-5e3ec99f87ed)

2. **Push your code to GitHub**
   - Include `Dockerfile`, `buildspec.yml`, and source code

3. **Create a CodeBuild Project**
   - Connect to your GitHub repo
   - Use Docker-enabled image (`aws/codebuild/standard:5.0`)
   - Set environment variables:
     - `AWS_REGION`
     - `AWS_ACCOUNT_ID`
![Screenshot 2025-04-14 174150](https://github.com/user-attachments/assets/36dfe814-a73a-4c26-bf70-cf3b4d751e70)

4. **Create a CodePipeline**
   - **Source**: GitHub
   - **Build**: CodeBuild project
   - **Deploy**: ECS (Fargate) with the ECS service and cluster
![Screenshot 2025-04-14 175148](https://github.com/user-attachments/assets/8bc50b9d-bbea-43c1-ba75-c58b397c689c)
![Screenshot 2025-04-14 175257](https://github.com/user-attachments/assets/b5492216-7998-4d32-abb5-1abfba250709)

---
## ✅ Public URL looks like
`http://34.201.1.66:8080/products/Message`
![Screenshot 2025-04-14 173912](https://github.com/user-attachments/assets/797669e5-f19e-4538-8725-ef0f3d7a3384)

## ✅ How it Works

- On every code push to GitHub, the pipeline gets triggered
- CodeBuild builds the Docker image and pushes it to ECR
- ECS pulls the latest image and updates the running container

---

## 🧹 Cleanup

To avoid ongoing charges, remember to delete:
- ECS service and cluster
- ECR repository
- CodeBuild and CodePipeline projects

---

## 📬 Feedback

Open an issue or pull request for suggestions or improvements.
