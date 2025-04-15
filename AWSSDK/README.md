# AWS SDK for Java - Getting Started Guide

This guide will help you get started with the AWS SDK for Java, enabling you to integrate various AWS services into your Java applications.

## Prerequisites

Before you begin, ensure you have the following:

- Java Development Kit (JDK) 8 or later
- Maven or Gradle for managing project dependencies
- AWS account with valid access keys (Access Key ID and Secret Access Key)

## Setup Instructions

### 1. Create a New Maven Project

Start by creating a new Maven project, if you don't have one already. Then, add the AWS SDK dependency in your `pom.xml` file:

```xml
 <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>s3</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>software.amazon.awssdk</groupId>
                    <artifactId>netty-nio-client</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>software.amazon.awssdk</groupId>
                    <artifactId>apache-client</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>sso</artifactId>
        </dependency>

        <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>ssooidc</artifactId>
        </dependency>

        <dependency>
            <groupId>software.amazon.awssdk</groupId>
            <artifactId>apache-client</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>commons-logging</groupId>
                    <artifactId>commons-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

### 2. Configure AWS Credentials

For AWS authentication, you can use one of the following methods:

- **Environment Variables**: Set `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`.
- **Shared Credentials File**: Store your credentials in `~/.aws/credentials` (Linux/macOS) or `C:\Users\<username>\.aws\credentials` (Windows).

Example of the credentials file:

```
[default]
aws_access_key_id = YOUR_ACCESS_KEY_ID
aws_secret_access_key = YOUR_SECRET_ACCESS_KEY
```

### 3. Initialize AWS SDK Client

You can now create an AWS SDK client. Here's how to initialize an S3 client:

```java
public class DependencyFactory {

    private DependencyFactory() {}

    /**
     * @return an instance of S3Client
     */
    public static S3Client s3Client() {
        return S3Client.builder()
                       .httpClientBuilder(ApacheHttpClient.builder())
                       .build();
    }
}
```

### 4. Running Your Application

Once your project is set up, you can build and run the application using Maven:

```bash
mvn clean install
Run it
```

## Example: List S3 Buckets

Here's a simple Java example to Create,upload,clean S3 buckets in your AWS account:

```java
package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class Handler {
    private final S3Client s3Client;

    public Handler() {
        s3Client = DependencyFactory.s3Client();
    }

    public void sendRequest() {
        String bucket = "bucket" + System.currentTimeMillis();
        String key = "images.jpeg";

        createBucket(s3Client, bucket);

        System.out.println("Uploading object...");
        Path imagePath = Paths.get("D:/images.jpeg");
        s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(key)
                .build(),
                RequestBody.fromFile(imagePath));

        System.out.println("Upload complete");
        System.out.printf("%n");

        // cleanUp(s3Client, bucket, key);

        System.out.println("Closing the connection to {S3}");
        s3Client.close();
        System.out.println("Connection closed");
        System.out.println("Exiting...");
    }

    public static void createBucket(S3Client s3Client, String bucketName) {
        try {
            s3Client.createBucket(CreateBucketRequest
                    .builder()
                    .bucket(bucketName)
                    .build());
            System.out.println("Creating bucket: " + bucketName);
            s3Client.waiter().waitUntilBucketExists(HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build());
            System.out.println(bucketName + " is ready.");
            System.out.printf("%n");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public static void cleanUp(S3Client s3Client, String bucketName, String keyName) {
        System.out.println("Cleaning up...");
        try {
            System.out.println("Deleting object: " + keyName);
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(keyName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            System.out.println(keyName + " has been deleted.");
            System.out.println("Deleting bucket: " + bucketName);
            DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
            s3Client.deleteBucket(deleteBucketRequest);
            System.out.println(bucketName + " has been deleted.");
            System.out.printf("%n");
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        System.out.println("Cleanup complete");
        System.out.printf("%n");
    }
}
```
![Screenshot 2025-04-14 011635](https://github.com/user-attachments/assets/b54ea099-1145-401d-9c20-2bb895a79b81)

## You can see bucket created and images uploaded in your aws account
![Screenshot 2025-04-14 011610](https://github.com/user-attachments/assets/0f6f6a50-2f64-4d52-8b38-500a1ad1b0ef)


### 5. Additional Resources

For further details on using the AWS SDK for Java, refer to the following resources:

- [Official AWS SDK for Java Developer Guide](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html)
- [AWS SDK for Java GitHub Repository](https://github.com/aws/aws-sdk-java)
