cd aodb
./gradlew clean bootJar -Pdev -Pswagger jibDockerBuild 
cd ..

cd flight
./gradlew clean bootJar -Pdev -Pswagger jibDockerBuild
cd ..

cd weather
./gradlew clean bootJar -Pdev -Pswagger jibDockerBuild 
cd ..

docker image tag aodb eamonfoy/aodb
docker push eamonfoy/aodb
docker image tag flight eamonfoy/flight
docker push eamonfoy/flight
docker image tag weather eamonfoy/weather
docker push eamonfoy/weather
