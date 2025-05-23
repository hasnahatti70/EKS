pipeline {
    agent any
    tools { maven 'Maven' }
    environment { SONARQUBE = 'SonarQube-10' }

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

        stage('Quality Gate') {
            steps {
                timeout(time: 3, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Déploiement sur OpenShift') {
            steps {
                sh 'oc start-build formulaire --from-file=formulaire/target/formulaire-0.0.1-SNAPSHOT.jar --follow'
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
