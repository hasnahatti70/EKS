pipeline {
    agent any

    tools {
        maven 'Maven' // Le nom défini dans Jenkins > Global Tool Configuration
    }

    environment {
        SONARQUBE = 'SonarQube-v10' // Le nom du serveur défini dans Jenkins > System > SonarQube
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
                    sh 'mvn clean package'
                }
            }
        }

        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    dir('formulaire') {
                        sh 'mvn sonar:sonar'
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
