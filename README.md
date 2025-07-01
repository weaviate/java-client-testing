# Weaviate Java Client - User testing

A starter project for testing the Java client v6 (alpha2 release).

## Running the Java program

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="io.weaviate.Main"
```

## GitHub Codespaces

### Java version

Run the following command to check if you are using Java 17:

```
java --version
```

To install and use Java 17 for this project, make sure to run: 

```bash
sudo apt update
sudo apt install openjdk-17-jdk

echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc
```

### Running Weaviate

To start a Weaviate instance with the provided `docker-compose.yml` configuration:
1. Install the Docker extension in VS Code
1. Right click the `docker-compose.yml` configuration and select `Compose Up`
1. Wait for the Docker process to start up in the terminal
