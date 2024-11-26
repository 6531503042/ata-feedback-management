pipeline {
    agent any

    environment {
        JAVA_HOME = tool 'JDK21'
        GRADLE_HOME = tool 'Gradle8'
        PATH = "${JAVA_HOME}/bin:${GRADLE_HOME}/bin:${PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                checkout scm
            }
        }

        stage('Build & Test Services') {
            parallel {
                stage('User Service') {
                    stages {
                        stage('Build') {
                            steps {
                                sh './gradlew :user-service:clean :user-service:build -x test'
                            }
                        }

                        stage('Test') {
                            steps {
                                sh './gradlew :user-service:test'
                            }
                            post {
                                always {
                                    junit '**/user-service/build/test-results/test/*.xml'
                                    jacoco(
                                        execPattern: '**/user-service/build/jacoco/*.exec',
                                        classPattern: '**/user-service/build/classes/java/main',
                                        sourcePattern: '**/user-service/src/main/java'
                                    )
                                }
                            }
                        }

                        stage('Code Quality') {
                            steps {
                                sh './gradlew :user-service:checkstyleMain'
                                recordIssues(tools: [checkStyle(pattern: '**/user-service/build/reports/checkstyle/*.xml')])
                            }
                        }

                        stage('Security Scan') {
                            steps {
                                sh './gradlew :user-service:dependencyCheckAnalyze'
                                dependencyCheckPublisher pattern: '**/user-service/build/reports/dependency-check-report.xml'
                            }
                        }
                    }
                }

                stage('Feedback Service') {
                    stages {
                        stage('Build') {
                            steps {
                                sh './gradlew :feedback-service:clean :feedback-service:build -x test'
                            }
                        }

                        stage('Test') {
                            steps {
                                sh './gradlew :feedback-service:test'
                            }
                            post {
                                always {
                                    junit '**/feedback-service/build/test-results/test/*.xml'
                                    jacoco(
                                        execPattern: '**/feedback-service/build/jacoco/*.exec',
                                        classPattern: '**/feedback-service/build/classes/java/main',
                                        sourcePattern: '**/feedback-service/src/main/java'
                                    )
                                }
                            }
                        }

                        stage('Code Quality') {
                            steps {
                                sh './gradlew :feedback-service:checkstyleMain'
                                recordIssues(tools: [checkStyle(pattern: '**/feedback-service/build/reports/checkstyle/*.xml')])
                            }
                        }

                        stage('Security Scan') {
                            steps {
                                sh './gradlew :feedback-service:dependencyCheckAnalyze'
                                dependencyCheckPublisher pattern: '**/feedback-service/build/reports/dependency-check-report.xml'
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build succeeded!'
            // Add notifications here
        }
        failure {
            echo 'Build failed!'
            // Add notifications here
        }
    }

    triggers {
        pollSCM('H/5 * * * *') // Poll every 5 minutes
    }
} 