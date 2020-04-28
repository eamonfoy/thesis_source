cd aodb
./gradlew bootJar -Pprod -Pswagger jibDockerBuild 
cd ..

cd flight
./gradlew bootJar -Pprod -Pswagger jibDockerBuild 
cd ..

cd weather
./gradlew bootJar -Pprod -Pswagger jibDockerBuild 
cd ..

docker image tag aodb eamonfoy/aodb
docker push eamonfoy/aodb
docker image tag flight eamonfoy/flight
docker push eamonfoy/flight
docker image tag weather eamonfoy/weather
docker push eamonfoy/weather