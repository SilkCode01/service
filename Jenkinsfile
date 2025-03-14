pipeline {
        agent any
        environment {
            DOCKER_USERNAME = credentials("docker_username")
            DOCKER_PASSWORD = credentials("docker_password")
            GITHUB_TOKEN = credentials("github_token")
            GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
            MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
            MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
            PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()

        }

        stages {
            stage('Build & Test') {
                steps {
                    sh './gradlew clean build'
                }
            }

            stage('Tag image') {
                steps {
                       sh "docker build -t ${DOCKER_USERNAME}/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
//                     script {
//                         sh([script: 'git fetch --tag', returnStdout: true]).trim()
//                         env.MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 1', returnStdout: true]).trim()
//                         env.MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 2', returnStdout: true]).trim()
//                         env.PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1 | cut -d . -f 3', returnStdout: true]).trim()
//                         env.IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
//                         env.GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
//                             GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
//                             MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
//                             MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
//                             PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()
//                    }
//                      sh "docker build -t ${DOCKER_USERNAME}/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."

                       withCredentials([string(credentialsId: 'dockerhub-pass', variable: 'DOCKER_PASSWORD')]) {
                            sh '''
                              docker login docker.io -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
                              docker push ${DOCKER_USERNAME}/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION}
                            '''
                       }
//
                       sh "git tag ${env.IMAGE_TAG}"
                       sh "git push https://$GITHUB_TOKEN@github.com/SilkCode01/service.git ${env.IMAGE_TAG}"
                }
            }
            stage('Run Application') {
                        steps {
                            sh "IMAGE_TAG=${env.IMAGE_TAG} docker-compose up -d hello"
                        }
            }


        }

}

