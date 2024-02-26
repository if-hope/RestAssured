pipeline {
    agent any
    environment{
        QA_PASS="Apple"
    }
    parameters {
        booleanParam defaultValue: true, description: 'browser mode', name: 'headless'
        choice choices: ['safari', 'chrome', 'firefox'], name: 'browser'
        string defaultValue: 'http://localhost:3333/', name: 'URL'
    }
    tools {
        maven 'maven_3_9_6'
        jdk 'JDK_17'
    }

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
                echo "Build number=$env.BUILD_ID"
            }
        }
        stage('env variables') {
            steps{
                sh 'printenv'
            }

        }
        stage('Get browser name') {
            when {
                environment name: 'headless', value: 'true'
            }
            steps{
                sh 'echo ${browser}'
            }
        }
        stage('Parallel tests') {
            parallel {
                stage('env variables') {
                  steps {
                      sh 'printenv'
                    }
                }
                stage('print hello') {
                    steps {
                        echo 'Hello World'
                    }
                }

            }

        }
        stage('Get maven version') {
            steps {
                sh 'mvn --version'
            }
        }
        stage('Get maven version with plagin') {
            steps {
                withMaven(maven: 'maven_3_9_6', jdk: 'JDK_17'){
                sh 'mvn --version'
                }
            }
        }
        stage('Trigger job') {
            steps{
                build job: 'SecondJob', parameters: [[$class: 'StringParameterValue', name: 'browser', value: 'firefox']]
            }
        }
        stage('Run with Maven options') {
            steps {
                withMaven(maven: 'maven_3_9_6', jdk: 'JDK_17'){
                    sh 'mvn clean test -Dsuite=${suite_name}'
                }
            }
        }
    }
    post{
        always{
          allure([
              includeProperties: false,
              jdk: '',
              properties: [],
              reportBuildPolicy: 'ALWAYS',
              results: [[path: 'target/allure-results']]
            ])
        }
    }
}