@Library('meshkorea') _

pipeline {
    agent {
        kubernetes {
            yaml """
              apiVersion: v1
              kind: Pod
              spec:
                containers:
                - name: git
                  image: alpine/git:v2.26.2
                  command:
                  - cat
                  tty: true
                - name: corretto-jdk
                  image: 200327251464.dkr.ecr.ap-northeast-2.amazonaws.com/devops/corretto:17-al2-full.v.1.0
                  command:
                  - cat
                  tty: true
                  volumeMounts:
                    - mountPath: /root
                      name: jdk
                    - mountPath: /var/run/docker.sock
                      name: dockersock
                volumes:
                - name: dockersock
                  hostPath:
                    path: /var/run/docker.sock
                - name: jdk
                  hostPath:
                    path: /local
                serviceAccountName: jenkins
                hostAliases:
                  - ip: "10.5.1.45"
                    hostnames:
                    - "nexus.mm.meshkorea.net"
            """
            defaultContainer 'git'
        }
    }

    options {
        timeout(time: 1, unit: 'HOURS')
    }

    environment {
        JOB = "${env.JOB_NAME.split('/')[0]}"
    }

    stages {
        stage('Init') {
            steps {
                script {
                    env.GLOBAL_STAGE_NAME = 'Init'
                    echo "git tag: ${env.BRANCH_NAME}"
                }
            }
        }

        stage('Gradle build') {
            steps {
                script {
                    env.GLOBAL_STAGE_NAME = 'Gradle build'

                    container('corretto-jdk') {
                        withCredentials([file(credentialsId: 'nexus', variable: 'nexus')]) {
                            sh "[ ! -d \"~/.gradle\" ] && mkdir -p ~/.gradle"
                            sh "cp \$nexus ~/.gradle/gradle.properties"
                        }

                        sh "./gradlew clean build -x order:clients:clean -x order:clients:build -x product:clients:clean -x product:clients:build -x delivery:clients:clean -x delivery:clients:build -x payment:clients:clean -x payment:clients:build"
                    }
                }
            }
        }
    }
}
