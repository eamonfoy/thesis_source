WARNING! Kubernetes configuration generated, but no Jib cache found
If you forgot to generate the Docker image for this application, please run:
To generate the missing Docker image(s), please run:
  ./gradlew bootJar -Pprod jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/aodb
  ./gradlew bootJar -Pprod jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/flight
  ./gradlew bootJar -Pprod jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/weather


WARNING! You will need to push your image to a registry. If you have not done so, use the following commands to tag and push the images:
  docker image tag aodb eamonfoy/aodb
  docker push eamonfoy/aodb
  docker image tag flight eamonfoy/flight
  docker push eamonfoy/flight
  docker image tag weather eamonfoy/weather
  docker push eamonfoy/weather

INFO! Alternatively, you can use Jib to build and push image directly to a remote registry:
  ./gradlew bootJar -Pprod jib -Djib.to.image=eamonfoy/aodb in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/aodb
  ./gradlew bootJar -Pprod jib -Djib.to.image=eamonfoy/flight in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/flight
  ./gradlew bootJar -Pprod jib -Djib.to.image=eamonfoy/weather in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/weather

You can deploy all your apps by running the following script:
  bash kubectl-apply.sh

Use these commands to find your application's IP addresses:
  kubectl get svc aodb -n aodb

INFO! Congratulations, JHipster execution is complete!
INFO! Deployment: child process exited with code 0
   create docker-compose.yml
   create README-DOCKER-COMPOSE.md
   create jhipster-registry.yml
   create central-server-config/application.yml

WARNING! Docker Compose configuration generated, but no Jib cache found
If you forgot to generate the Docker image for this application, please run:
To generate the missing Docker image(s), please run:
  ./gradlew bootJar -Pprod jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/aodb
  ./gradlew bootJar -Pprod jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/flight
  ./gradlew bootJar -Pprod jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_build_steps/step4/weather

You can launch all your infrastructure by running : docker-compose up -d
INFO! Congratulations, JHipster execution is complete!
INFO! Deployment: child process exited with code 0