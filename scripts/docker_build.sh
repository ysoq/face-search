version='2.0.1'
SHELL_FOLDER=$(cd "$(dirname "$0")";pwd)
cd ${SHELL_FOLDER}

#编译项目
cd ../
mvn clean package -DskipTests -Pdocker

#编译
docker build -f scripts/docker/Dockerfile -t visual/face-search:${version} .
docker tag visual/face-search:${version} visual/face-search:latest

#中央仓库
docker tag visual/face-search:${version} divenswu/face-search:${version}
docker tag visual/face-search:${version} divenswu/face-search:latest