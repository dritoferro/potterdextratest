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
      steps {
        echo 'Loging into Docker Hub and pushing image'
        script{
          withRegistry('', dockerhub){
            sh "docker push dritoferro/potterdextratest:latest"
          }
        }
      }
    }
  }
