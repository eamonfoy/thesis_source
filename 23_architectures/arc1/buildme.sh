cd aodb
npm install
sudo ./gradlew clean bootJar -Pprod -Pswagger -Pzipkin -Pchaos-monkey jibDockerBuild 
cd ..

cd flight
npm install
sudo ./gradlew clean bootJar -Pprod -Pswagger -Pzipkin -Pchaos-monkey jibDockerBuild
cd ..

cd weather
npm install
sudo ./gradlew clean bootJar -Pprod -Pswagger -Pzipkin -Pchaos-monkey jibDockerBuild 
cd ..

docker image tag aodb eamonfoy/aodb
docker push eamonfoy/aodb
docker image tag flight eamonfoy/flight
docker push eamonfoy/flight
docker image tag weather eamonfoy/weather
docker push eamonfoy/weather

