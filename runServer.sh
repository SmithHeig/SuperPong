# Build jar Lib
mvn clean install -f SuperPong/Lib/

# Build jar Server
mvn clean install -f SuperPong/Server/

# Build jar Client
mvn clean install -f SuperPong/Client/
echo "copie du jar du server"
mkdir Docker/server-image/src
cp ./SuperPong/Server/target/Server-1.0-SNAPSHOT.jar ./Docker/server-image/src/Server-1.0-SNAPSHOT.jar

echo "built de l'image server"
docker build -t superpong/server ./Docker/server-image/

echo "run de l'image"
docker run -p 666:666 superpong/server

# A décommenter si docker run -d
#java -jar ./SuperPong/Client/target/Client-1.0-SNAPSHOT.jar
