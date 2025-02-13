## Installation Guide 

This document gives the instructions to install the Rasta ME, i.e., the metadata enricher API

## Local installation
### System Requirements
The software requirements can be summarized as:

* Microsoft Windows, Mac OS X or Unix Operating Systems and derived are supported
* Latest OpenJDK 21 (or other Java distribution like Eclipse Adoptium https://adoptium.net/) is recommended. 
* Latest [Apache Maven](https://maven.apache.org/) installed.

### Start Rasta ME API
To launch the Rasta ME API, run these maven commands that will start the application on the port `9090`:
1. Open a terminal in the directory where you downloaded the source code and type the following command:

    ```
    mvn clean compile
    ```

2. Open a terminal in the directory where you downloaded the source code and type the following command: 


    ```
    mvn spring-boot:run
    ```
    ```
## Docker installation
### System Requirements
Docker has to be installed to your machine

### Start Rasta ME API application
To launch the Rasta ME API, run this docker command that will create a container containing the Rasta ME api on the port `9090`:
1. Set the Open AI key within the file `docker-compose.yaml`, i.e., add the key to the `spring.ai.openai.api-key` properties 
2. Open a terminal in the directory where you downloaded the source code and type the following command:

    ```
    docker compose up
    ```
