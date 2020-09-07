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
      when {
        branch 'master'
      }
      steps {
        echo 'Loging into Docker Hub and pushing image'
        script {
          withDockerRegistry(url: '', credentialsId: 'dockerhub'){
            sh "docker push dritoferro/potterdextratest:latest"
          }
        }

        echo 'Cleaning up'
        sh 'docker image prune -f'
        echo 'Success!!!'
      }
    }

    stage('Kubernetes') {
      when {
        branch 'master'
      }
      steps {
        echo 'Updating pods on Kubernetes'
        script {
          withKubeConfig([credentialsId: 'kubernetes-config']){
            sh 'kubectl apply -f kubernetes/app-deployment.yaml'
          }
        }

      }
    }

  }
}