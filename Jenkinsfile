pipeline {
    agent any

    environment {
        JAR_FILE = 'target/*.jar'
        PROJECT_DIR = 'Backend/kidswallet'
        IMAGE_NAME = 'rain5191/kidswallet'
        CONTAINER_NAME = 'kidswallet-container'  // 컨테이너 이름을 변수로 관리
    }

    stages {
        stage('Checkout') {
            steps {
                // GitLab에서 소스 코드 체크아웃 (자격 증명 추가)
                git credentialsId: 'kidswallet', branch: 'develop/be', url: 'https://lab.ssafy.com/s11-final/S11P31E201.git'
            }
        }

        stage("Copy application.properties"){
             steps {
                withCredentials([file(credentialsId: 'kidwallet_properties', variable: 'properties')]) {
        	        script {
                        sh 'chmod +r $properties'
                        echo 'Properties file location: $properties'
        	            sh 'rm -f ${PROJECT_DIR}/src/main/resources/application.properties'
                        sh 'cp $properties ${PROJECT_DIR}/src/main/resources/application.properties'
                            
                    }
                }
            }
        }

         stage("Copy index.html"){
             steps {
                withCredentials([file(credentialsId: 'HTMLFILE', variable: 'html')]) {
        	        script {
                           // static 디렉토리가 없으면 생성
                        sh '''
                            if [ ! -d "${PROJECT_DIR}/src/main/resources/static" ]; then
                            mkdir -p ${PROJECT_DIR}/src/main/resources/static
                            fi
                            '''
                        sh 'chmod +r $html'
                        echo 'html file location: $html'
        	            sh 'rm -f ${PROJECT_DIR}/src/main/resources/static/index.html'
                        sh 'cp $html ${PROJECT_DIR}/src/main/resources/static/index.html'
                    }
                }
            }
        }
        
      stage('Build') {
            steps {
               dir("${PROJECT_DIR}") {
                    sh 'chmod +x ./gradlew' // Gradle Wrapper 실행 권한 부여
                    sh './gradlew clean build' // 빌드 실행
                    echo 'Build completed successfully!'
                }
            }
        }
        
        stage('Docker Cleanup') {
            steps {
                script {
                   echo 'Checking for existing containers...'
                    // 기존 컨테이너 중지 및 삭제 (있다면)
                    sh """
                    if [ \$(docker ps -aq -f name=${CONTAINER_NAME}) ]; then
                        docker stop ${CONTAINER_NAME} || true
                        docker rm ${CONTAINER_NAME} || true
                    fi
                    """
                }
            }
        }
        stage('Docker Build') {
            steps {
                script {
                  echo 'Building Docker image from Dockerfile...'
                    // Dockerfile을 사용하여 이미지 빌드
                    sh "docker build -t ${IMAGE_NAME} ${PROJECT_DIR}"  // Dockerfile이 있는 디렉토리 지정
                }
            }
        }
         stage('Run Container') {
             steps {
                script {
                    // Docker Compose를 사용하여 컨테이너 실행
                    sh "docker-compose -f ${PROJECT_DIR}/docker-compose.yml up -d"
                }
            }
        }
    }
     post{
        success {
            script {
                // 최근 커밋의 작성자 정보 가져오기
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (
                    color: 'good',
                    message: "# :jenkins1: \n ### 빌드 성공: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})(<${env.BUILD_URL}|Details>)",
                    endpoint: 'https://meeting.ssafy.com/hooks/wie4oueox3dwjpa58rk5mpf9ac',
                    channel: 'E201-Build'
            )
        }
    }

        failure {
            script {
                // 최근 커밋의 작성자 정보 가져오기
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (
                    color: 'danger',
                    message: "# :jenkins5: \n ### 빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)",
                    endpoint: 'https://meeting.ssafy.com/hooks/wie4oueox3dwjpa58rk5mpf9ac',
                    channel: 'E201-Build'
                )
            }
        }
    }
    
}
