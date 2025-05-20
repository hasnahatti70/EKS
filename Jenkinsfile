pipeline {
    agent any

    tools {
        maven 'Maven' // Nom défini dans Jenkins > Global Tool Configuration
    }

    environment {
        SONARQUBE = 'SonarQube-v10' // Le nom défini dans "Manage Jenkins > Configure System"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/hasnahatti70/EKS.git'
            }
        }

        stage('Build avec Maven') {
            steps {
                dir('formulaire') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Tests') {
            steps {
                dir('formulaire') {
                    sh 'mvn test'
                }
            }
        }

        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv("${SONARQUBE}") {
                    withCredentials([string(credentialsId: '8cc29e18-77c3-45bc-9f81-21ded76ad1d4', variable: 'TOKEN')]) {
                        dir('formulaire') {
                            sh 'mvn sonar:sonar -Dsonar.projectKey=formulaire -Dsonar.token=$TOKEN'
                        }
                    }
                }
            }
        }
    }

    post {
        failure {
            echo 'La pipeline a échoué ❌'
        }
        success {
            echo 'Pipeline terminée avec succès ✅'
        }
    }
}
