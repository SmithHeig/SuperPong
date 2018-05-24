echo "copie du jar du server"
cp ./SuperPong/Server/target/Server-1.0-SNAPSHOT.jar ./Docker/server-image/src/Server-1.0-SNAPSHOT.jar

echo "built de l'image server"
docker build -t superpong/server ./Docker/server-image/

echo "run de l'image"
docker run -p 666:666 superpong/server
