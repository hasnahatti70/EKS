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

        stage('Gitleaks Scan') {
            steps {
                bat '''
                echo 🔍 Utilisation de Gitleaks local installé...
                "C:\\Users\\MTechno\\Downloads\\gitleaks_8.26.0_windows_x64\\gitleaks.exe" detect --source=. --verbose --report-format=json --report-path=gitleaks-report.json || exit /b 0

                echo 📄 Résultats du scan Gitleaks :
                type gitleaks-report.json || echo ⚠️ Aucun résultat trouvé.
                '''
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

        stage('Déploiement sur OpenShift') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig-hasna', variable: 'KUBECONFIG')]) {
                    script {
                        dir('formulaire') {
                            sh 'oc start-build formulaire --from-file=target/formulaire-0.0.1-SNAPSHOT.jar --follow'
                        }
                        sh 'oc apply -f formulaire-all.yaml'
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
        always {
            archiveArtifacts artifacts: 'gitleaks-report.json', allowEmptyArchive: true
        }
    }
}
