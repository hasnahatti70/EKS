pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        SONARQUBE = 'SonarQube-10'
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
                    dir('formulaire') {
                        sh 'mvn sonar:sonar -Dsonar.projectKey=formulaire'
                    }
                }
            }
        }

        // ✅ Option 1 - Copie du JAR vers un dossier partagé pour OpenShift
        stage('Déploiement Continu (CD) - Copie .jar') {
            steps {
                dir('formulaire') {
                    sh 'cp target/formulaire-0.0.1-SNAPSHOT.jar /mnt/nfs/deploy/formulaire/'
                }
            }
        }

        // ✅ Option 2 - Simulation si pas de droit réel
        stage('CD (simulé si accès restreint)') {
            steps {
                echo '✅ Simulation de livraison du .jar vers OpenShift (accès restreint ou environnement de test)'
            }
        }
    }

    post {
        failure {
            echo '❌ La pipeline a échoué.'
        }
        success {
            echo '✅ Pipeline terminée avec succès.'
        }
    }
}
