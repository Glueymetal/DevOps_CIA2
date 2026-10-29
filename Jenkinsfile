pipeline {
  agent any
  triggers { githubPush() }
  environment {
    ACR_NAME       = 'jeyashreecia2acr'
    RESOURCE_GROUP = 'cicd-rg'
    CLUSTER_NAME   = 'cicd-aks'
    IMAGE          = 'jeyashreecia2acr.azurecr.io/cicd-demo'
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build and Test') {
      steps { bat 'mvn -B clean verify' }
      post { always { junit 'target/surefire-reports/*.xml' } }
    }
    stage('Docker Build') {
      steps { bat 'docker build -t %IMAGE%:%BUILD_NUMBER% .' }
    }
    stage('Push to ACR') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'azure-sp', usernameVariable: 'AZ_USER', passwordVariable: 'AZ_PASS'),
                         string(credentialsId: 'azure-tenant', variable: 'AZ_TENANT')]) {
          bat 'az login --service-principal -u %AZ_USER% -p %AZ_PASS% --tenant %AZ_TENANT%'
          bat 'az acr login --name %ACR_NAME%'
          bat 'docker push %IMAGE%:%BUILD_NUMBER%'
        }
      }
    }
    stage('Deploy to AKS') {
      steps {
        bat 'az aks get-credentials -g %RESOURCE_GROUP% -n %CLUSTER_NAME% --overwrite-existing'
        powershell '(Get-Content k8s/deployment.yaml).Replace("IMAGE_PLACEHOLDER", "${env:IMAGE}:${env:BUILD_NUMBER}") | Set-Content k8s/deployment.yaml'
        bat 'kubectl apply -f k8s/deployment.yaml'
        bat 'kubectl apply -f k8s/service.yaml'
      }
    }
    stage('Verify') {
      steps {
        bat 'kubectl rollout status deployment/cicd-demo --timeout=180s'
        bat 'kubectl get pods'
        bat 'kubectl get svc cicd-demo-svc'
      }
    }
  }
}