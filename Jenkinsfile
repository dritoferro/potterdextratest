pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        withGradle() {
          echo 'Building project'
          sh './gradlew build'
        }

      }
    }

    stage('Docker') {
      environment {
        registryCredentials = 'dockerhub'
      }
      steps {
        echo 'Building docker image'
        sh 'docker build -t "dritoferro/potterdextratest:latest" .'
        echo 'Pushing image to Docker Hub with credentials "${env.registryCredentials}" "${registryCredentials}"'
        sh 'docker.withRegistry(\'https://registry.hub.docker.com\', ${env.registryCredentials}){docker push dritoferro/potterdextratest:latest}'
      }
    }

  }
}