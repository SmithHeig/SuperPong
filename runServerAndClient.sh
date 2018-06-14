docker kill $(docker ps -q -a)
docker rm $(docker ps -a -q)

#Build project and childrens
echo "building project"
mvn clean install -f SuperPong/pom.xml

echo "copie du jar du server"
mkdir Docker/images/src
cp ./SuperPong/Server/target/Server-1.0-SNAPSHOT.jar ./Docker/images/src/Server-1.0-SNAPSHOT.jar

echo "copie du jar de la lib"
cp ./SuperPong/Lib/target/lib-1.0-SNAPSHOT.jar ./Docker/images/src/lib-1.0-SNAPSHOT.jar

echo "copie du jar du client"
cp ./SuperPong/Client/target/Client-1.0-SNAPSHOT.jar ./Docker/images/src/Client-1.0-SNAPSHOT.jar

echo "built de l'image server"
docker build -t superpong/server ./Docker/server-image/

echo "run de l'image"
docker run --name server-superpong -d -p 6666:6666 superpong/server

# A décommenter si docker run -d
java -jar ./SuperPong/Client/target/Client-1.0-SNAPSHOT.jar
