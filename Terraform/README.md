
# 🌐 Terraform on AWS - Getting Started

This project demonstrates how to provision basic AWS infrastructure using [Terraform](https://www.terraform.io/), an Infrastructure as Code (IaC) tool.

## 🚀 What You'll Learn

- How to install and configure Terraform
- How to authenticate with AWS using IAM credentials
- How to write basic Terraform configurations
- How to initialize, plan, and apply Terraform changes
- How to manage and destroy infrastructure safely

## 📦 Requirements

- [Terraform](https://developer.hashicorp.com/terraform/downloads)
- AWS account with IAM credentials (Access Key ID & Secret Access Key)
- AWS CLI (optional, but recommended)
- A text editor (e.g., VS Code)

## 📁 Project Structure

```bash
.
├── main.tf        # Main Terraform configuration file
├── variables.tf   # Variable definitions
├── outputs.tf     # Output values
└── terraform.tfstate # Terraform's state file (auto-generated after apply)
```

## 🔧 Setup Instructions

1. **Clone the repo** (or create your own directory and `.tf` files):

   ```bash
   git clone https://github.com/maheshwar04/Terraform.git
   cd Terraform
   ```

2. **Configure AWS credentials**:

   Set your AWS credentials as environment variables:

   ```bash
   export AWS_ACCESS_KEY_ID="your-access-key"
   export AWS_SECRET_ACCESS_KEY="your-secret-key"
   ```

   Or use `aws configure` if AWS CLI is installed.

3. **Initialize Terraform**:

   ```bash
   terraform init
   ```

4. **Review the execution plan**:

   ```bash
   terraform plan
   ```

5. **Apply the configuration**:

   ```bash
   terraform apply
   ```

   Confirm with `yes` when prompted.

6. **Destroy the infrastructure**:

   When done, clean up your resources to avoid charges:

   ```bash
   terraform destroy
   ```

## 🧾 Notes

- This tutorial creates basic AWS infrastructure like an EC2 instance.
- Ensure you are within AWS Free Tier limits to avoid charges.
- Don't commit `terraform.tfstate` to Github.

## 📚 References

- [Terraform Documentation](https://developer.hashicorp.com/terraform/docs)
- [AWS Provider Docs](https://registry.terraform.io/providers/hashicorp/aws/latest/docs)
