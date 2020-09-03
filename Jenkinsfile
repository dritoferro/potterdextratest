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
        echo 'Loging into Docker Hub'
        sh '''"withCredentials([usernamePassword(credentialsId: \'dockerhub\', passwordVariable: \'pass\', usernameVariable: \'user\')]) {
    docker login docker.io --username=$user --password=$pass
}"'''
          echo 'Pushing image to Docker Hub'
          sh 'docker push dritoferro/potterdextratest:latest'
        }
      }

    }
  }