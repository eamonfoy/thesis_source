# Building and running a jhipster monilith

1. login in to docker hub
```
 docker login --password "?|i,V[k35^cn" --username "eamonfoy"
```

2. First, make sure that MySQL DB is running from the previous step; otherwise, start it using 
```
docker-compose -f src/main/docker/mysql.yml up -d 
```

2. Now, let's create an executable archive for the prod profile by running:
```
./gradlew clean bootJar -Pprod -Pswagger
```

3. Once the build is successful, there will be an archive (JAR) created under
build/libs . The xxxxx-0.0.1-SNAPSHOT.jar file is an executable archive
that can be run directly on a JVM.

4. Let's use the executable archive. Just run:

```
java -jar build/libs/store-0.0.1-SNAPSHOT.jar 
```


# Installing Docker on Ubuntu 19.4
First, add the GPG key for the official Docker repository to the system:
```
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

Add the Docker repository to APT sources:
```
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable edge"
```

Next, update the package database with the Docker packages from the newly added repo:

```
sudo apt-get update
```

Make sure you are about to install from the Docker repo instead of the default Ubuntu 19.04 repo:
```
apt-cache policy docker-ce
```

You should see output similar to the follow:


```
# Note that in this case only available version is beta.
docker-ce:
  Installed: (none)
  Candidate: 5:19.03.0~1.2.beta2-0~ubuntu-disco
  Version table:
     5:19.03.0~1.2.beta2-0~ubuntu-disco 500
        500 https://download.docker.com/linux/ubuntu disco/test amd64 Packages
     5:19.03.0~1.1.beta1-0~ubuntu-disco 500
        500 https://download.docker.com/linux/ubuntu disco/test amd64 Packages
```

Notice that docker-ce is not installed, but the candidate for installation is from the Docker repository for Ubuntu 19.04. The docker-ce version number might be different.
Finally, install Docker:

```
sudo apt-get install -y docker-ce
```

Docker should now be installed, the daemon started, and the process enabled to start on boot. Check that it’s running:

```
sudo systemctl status docker
```
The output should be similar to the following, showing that the service is active and running:

```
● docker.service - Docker Application Container Engine
   Loaded: loaded (/lib/systemd/system/docker.service; enabled; vendor preset: enabled)
   Active: active (running) since Tue 2019-04-23 04:48:18 UTC; 6s ago
     Docs: https://docs.docker.com
 Main PID: 4065 (dockerd)
    Tasks: 8
   Memory: 38.5M
   CGroup: /system.slice/docker.service
           └─4065 /usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock

Apr 23 04:48:18 ubuntu dockerd[4065]: time="2019-04-23T04:48:18.213391355Z" level=warning msg="Your kernel does not support cgroup rt runtime"
Apr 23 04:48:18 ubuntu dockerd[4065]: time="2019-04-23T04:48:18.213476146Z" level=warning msg="Your kernel does not support cgroup blkio weight"
Apr 23 04:48:18 ubuntu dockerd[4065]: time="2019-04-23T04:48:18.213560442Z" level=warning msg="Your kernel does not support cgroup blkio weight_device"
Apr 23 04:48:18 ubuntu dockerd[4065]: time="2019-04-23T04:48:18.213751416Z" level=info msg="Loading containers: start."
Apr 23 04:48:18 ubuntu dockerd[4065]: time="2019-04-23T04:48:18.345304920Z" level=info msg="Default bridge (docker0) is assigned with an IP address 172.17.0.0/16. Daemon option --bip can be used to set a preferred IP address"
Apr 23 04:48:18 ubuntu dockerd[4065]: time="2019-04-23T04:48:18.424995101Z" level=info msg="Loading containers: done."
Apr 23 04:48:18 ubuntu dockerd[4065]: time="2019-04-23T04:48:18.479526438Z" level=info msg="Docker daemon" commit=c601560 graphdriver(s)=overlay2 version=19.03.0-beta2
Apr 23 04:48:18 ubuntu dockerd[4065]: time="2019-04-23T04:48:18.479894604Z" level=info msg="Daemon has completed initialization"
Apr 23 04:48:18 ubuntu systemd[1]: Started Docker Application Container Engine.
```


# Executing the Docker Command Without Sudo (Optional)


By default, running the docker command requires root privileges — that is, you have to prefix the command with sudo. It can also be run by a user in the docker group, which is automatically created during the installation of Docker. If you attempt to run the docker command without prefixing it withsudoor without being in the docker group, you'll get an output like this:

```
docker: Cannot connect to the Docker daemon. Is the docker daemon running on this host?.
See 'docker run --help'.
```

If you want to avoid typing sudo whenever you run the docker command, add your username to the docker group:
```
sudo usermod -aG docker ${USER}
```

To apply the new group membership, you can log out of the server and back in, or you can type the following:

```
su ${USER}
```

You will be prompted to enter your user’s password to continue. Afterwards, you can confirm that your user is now added to the docker group by typing:

```
id -nG
```

Output:
```
username sudo docker
```

If you need to add a user to the docker group that you're not logged in as, declare that username explicitly using:
```
sudo usermod -aG docker username
```

The rest of this document assumes you are running the docker command as a user in the docker user group. If you choose not to, please prepend the commands with sudo.


more commands
```
WARNING! Kubernetes configuration generated, but no Jib cache found
If you forgot to generate the Docker image for this application, please run:
To generate the missing Docker image(s), please run:
  ./gradlew bootJar -Pprod -Pswagger jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_jhipster/step1/aodb
  ./gradlew bootJar -Pprod -Pswagger jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_jhipster/step1/flight
  ./gradlew bootJar -Pprod -Pswagger jibDockerBuild in /home/eamonfoy/Documents/projects/thesis_project/21_jhipster/step1/weather


WARNING! You will need to push your image to a registry. If you have not done so, use the following commands to tag and push the images:
  docker image tag aodb eamonfoy/aodb
  docker push eamonfoy/aodb
  docker image tag flight eamonfoy/flight
  docker push eamonfoy/flight
  docker image tag weather eamonfoy/weather
  docker push eamonfoy/weather

INFO! Alternatively, you can use Jib to build and push image directly to a remote registry:
  ./gradlew bootJar -Pprod jib -Djib.to.image=eamonfoy/aodb in /home/eamonfoy/Documents/projects/thesis_project/21_jhipster/step1/aodb
  ./gradlew bootJar -Pprod jib -Djib.to.image=eamonfoy/flight in /home/eamonfoy/Documents/projects/thesis_project/21_jhipster/step1/flight
  ./gradlew bootJar -Pprod jib -Djib.to.image=eamonfoy/weather in /home/eamonfoy/Documents/projects/thesis_project/21_jhipster/step1/weather

You can deploy all your apps by running the following script:
  bash kubectl-apply.sh

Use these commands to find your application's IP addresses:
  kubectl get svc aodb

```

