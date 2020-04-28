
## Thesis:  Eamon Foy

## Title: Using Service Mesh Technology within a Microservice architecture design

## University: CIT


[Please find the setup guide with all the instructions needed to set up the envionment](https://github.com/eamonfoy/thesis_source/blob/0d706fac4df5e9eacbf1c3eaecb7d5619b7a0130/Enviornment_Setup.pdf)


The following is an explaination of the structure and what each directory contains:

| Directory             | Description   | 
| -------------         |:------------- | 
| 01_system_build       | Envionment Build including the cluster setup | 
| 02_designs            | Contains the draw.io designs     | 
| 22_integration        | Integration compeonebnt to load flight and weather information      | 
| 23_architectures | Contaions the code for ARC1, ARC2 and ARC3 |
| 24_docker | Contains instruction on how to upload images to docker hub |
| 25_jmeter | Contains the JMeter tests and results |
| 26_ha-proxy | Contains the configuration for HA proxy to work with Istio and Spriong boot architectures |
| 28_postman | Contains the postman scripts to activate the Chaos Monkey  to simulate delays |
| 31_measure_effort | Contains the results of test 4, measuring the effort |


```
.
├── 01_system_build
│   └── kubernetes-vagrant_cluster
│       ├── dynamic_nfs_persistant_volume_creation
├── 02_designs
├── 22_integration
│   └── flifo
│       ├── lib
│       └── src
├── 23_architectures
│   ├── arc1
│   │   ├── aodb
│   │   ├── docker-compose
│   │   ├── flight
│   │   ├── kubernetes
│   │   └── weather
│   ├── arc2
│   │   ├── aodb
│   │   ├── docker-compose
│   │   ├── flight
│   │   ├── kubernetes
│   │   └── weather
│   └── arc3
│       ├── aodb
│       ├── docker-compose
│       ├── flight
│       ├── kubernetes
│       └── weather
├── 24_docker
├── 25_jmeter
│   ├── images
│   ├── results
│   │   ├── exp1_t1
│   │   ├── exp1_t2
│   │   ├── exp1_t3
│   │   ├── exp2_t1
│   │   ├── exp2_t2
│   │   └── exp3_t3
│   └── tests
├── 26_ha-proxy
│   ├── istio
│   └── springcloud
├── 28_postman
└── 31_measure_effort

```

