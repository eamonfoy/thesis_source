!/bin/bash

# cd aodb
# ./gradlew clean bootJar -Pprod -Pswagger jibDockerBuild 
# cd ..

# cd flight
# ./gradlew clean bootJar -Pprod -Pswagger jibDockerBuild
# cd ..

# cd weather
# ./gradlew clean bootJar -Pprod -Pswagger jibDockerBuild 
# cd ..

# docker image tag aodb eamonfoy/aodb
# docker push eamonfoy/aodb
# docker image tag flight eamonfoy/flight
# docker push eamonfoy/flight
# docker image tag weather eamonfoy/weather
# docker push eamonfoy/weather



usage(){
 cat << EOF

 Usage: $0 -f
 Build Flight service  and push to docker hub
[OR]
 Usage: $0 -w
 Build Weather service  and push to docker hub
[OR]
 Usage: $0 -a
 Build AODB service and push to docker hub
[OR]
 Usage: $0 -l
 Build ALL services and push to docker hub

EOF
exit 0
}

flight() {
    cd flight
    ./gradlew clean bootJar -Pprod jibDockerBuild
    cd ..
    docker image tag flight eamonfoy/flight
    docker push eamonfoy/flight
}

aodb() {
    cd aodb
    ./gradlew clean bootJar -Pprod jibDockerBuild 
    cd ..
    docker image tag aodb eamonfoy/aodb
    docker push eamonfoy/aodb
}

weather() {
    cd weather
    ./gradlew clean bootJar -Pprod  -Pchaos-monkey  jibDockerBuild 
    cd ..
    docker image tag weather eamonfoy/weather
    docker push eamonfoy/weather
}

all() {
    flight
    aodb
    weather
}


[[ "$@" =~ ^-[fawl]{1}$ ]]  || usage;

while getopts ":fawl" opt; do
    case ${opt} in
    f ) echo "Building Flight service and pushing to docker"; flight ;;
    a ) echo "Building AODB service and pushing to docker"; aodb ;;
    w ) echo "Building Weather service and pushing to docker"; weather ;;
    l ) echo "Building ALL services and pushing them to docker"; all ;;
    \? | * ) usage ;;
    esac
done


