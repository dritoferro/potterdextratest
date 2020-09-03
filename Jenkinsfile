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
        script {
          withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'pass', usernameVariable: 'user')]){
            sh "docker login docker.io --username=$user --password=$pass"
            sh "docker push dritoferro/potterdextratest:latest"
          }
        }

        echo 'Cleaning up'
        sh 'docker image prune -f'
        echo 'Success!!!'
      }
    }

  }
}