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

        stage('SonarQube Analysis with Token') {
            environment {
                SONAR_HOST_URL = 'https://sonarqube-v10-hasnahatti70-dev.apps.rm2.thpm.p1.openshiftapps.com/' // ← Remplace bien par l'URL réelle
            }
            steps {
                withCredentials([string(credentialsId: 'banking-app', variable: 'SONAR_TOKEN')]) {
                    dir('formulaire') {
                        sh '''
                        mvn sonar:sonar \
                          -Dsonar.projectKey=banking-app \
                          -Dsonar.projectName=banking-app \
                          -Dsonar.host.url=${SONAR_HOST_URL} \
                          -Dsonar.login=${SONAR_TOKEN}
                        '''
                    }
                }
            }
        }

        stage('Build Docker') {
            steps {
                script {
                    dir('formulaire') {
                        sh '''
                        echo 🐳 Build de l'image Docker...
                        docker build -t formulaire-app:latest .
                        '''
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
                            sh 'oc apply -f formulaire-all.yaml'
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
        always {
            archiveArtifacts artifacts: 'gitleaks-report.json', allowEmptyArchive: true
        }
    }
}
