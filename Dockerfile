FROM adoptopenjdk:8-jdk-hotspot
RUN mkdir /usr/app
WORKDIR /usr/app

COPY ./build/libs/dextrapotter.jar ./

CMD ["java", "-jar", "dextrapotter.jar"]