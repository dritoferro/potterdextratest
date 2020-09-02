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
        REGCREDENTIALS = 'dockerhub'
      }
      steps {
        echo 'Pushing image to Docker Hub'
        sh 'docker push dritoferro/potterdextratest:latest'
      }
    }

  }
}