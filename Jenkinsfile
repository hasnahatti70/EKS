pipeline {
    agent any

    tools {
        maven 'MAVEN' // Le nom que tu as défini dans Global Tool Configuration
    }

    environment {
        SONARQUBE = 'SonarQube-10'   // Nom défini dans "SonarQube servers"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build avec Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Analyse SonarQube') {
            steps {
                withSonarQubeEnv("${SONARQUBE}") {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=formulaire \
                        -Dsonar.projectName=formulaire \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src/main/java \
                        -Dsonar.tests=src/test/java \
                        -Dsonar.java.binaries=target/classes
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Analyse SonarQube terminée avec succès 🎉'
        }
        failure {
            echo 'La pipeline a échoué ❌'
        }
    }
}
