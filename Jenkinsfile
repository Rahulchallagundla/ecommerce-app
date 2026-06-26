pipeline {
    agent any

    environment {
        IMAGE_NAME = "ecommerce"
        IMAGE_TAG = "v2"
        AWS_REGION = "ap-south-1"
        AWS_ACCOUNT_ID = "466825169799"
        ECR_REPO = "466825169799.dkr.ecr.ap-south-1.amazonaws.com/ecommerce"
    }

    stages {

        stage('SCM Checkout') {
            steps {
                git 'https://github.com/Rahulchallagundla/ecommerce-app.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build -t $IMAGE_NAME:$IMAGE_TAG .
                '''
            }
        }

        stage('Login to Amazon ECR') {
            steps {
                withCredentials([
                    aws(
                        credentialsId: 'aws-creds',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                    )
                ]) {
                    sh '''
                        aws ecr get-login-password --region $AWS_REGION | \
                        docker login --username AWS --password-stdin \
                        $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                    '''
                }
            }
        }
        stage('Push Image') {
             steps {
                sh '''
                    docker tag $IMAGE_NAME:$IMAGE_TAG $ECR_REPO:$IMAGE_TAG
                    docker push $ECR_REPO:$IMAGE_TAG
                '''

            }
        }
        stage('Deploy to EKS') {
    steps {
        sh '''
            kubectl apply -f k8s/
        '''
    }
}

    }
}
