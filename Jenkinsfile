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

    stage('Docker Build') {
      steps {
        echo 'Building docker image'
        sh 'docker build -t "dritoferro/potterdextratest:latest" .'
      }
    }

    stage('Docker Push') {
      environment {
        DOCKER_HUB_LOGIN = 'credentials(dockerhub)'
      }
      steps {
        echo 'Loging into Docker Hub'
        sh 'docker login docker.io --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
        echo 'Pushing image to Docker Hub'
        sh 'docker push dritoferro/potterdextratest:latest'
      }
    }

  }
}