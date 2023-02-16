## 배포 정리 문서

# 1. node 설치

```terminal
sudo apt update
sudo apt-get install nodejs -y
```

# 2. docker && docker-compose 설치

https://docs.docker.com/engine/install/ubuntu/

# 3. install openvidu

https://docs.openvidu.io/en/2.25.0/deployment/ce/on-premises/#2-deployment
```terminal
sudo su
cd /opt
curl https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh | bash
```

# 4. change openvidu config

`openvidu` 폴더 안의 파일을 `/opt/openvidu` 로 옮겨준다.
* `openvidu/docker-compose.yml`
* `openvidu/custom-nginx.conf`
* `openvidu/nginx.conf`
* `openvidu/.env`

# 5. change openvidu .env file

`.env` 파일 안에 `{}`로 되어 있는 부분을 원하는 값으로 넣어준다.

# 6. run openvidu

```terminal
sudo su
cd /opt/openvidu
./openvidu start
```

# 7. install rabbitmq

```terminal
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -p 61613:61613 rabbitmq:3.11-management
docker exec -it rabbitmq rabbitmq-plugins enable rabbitmq_stomp
```

# 8. install mongodb

```terminal
docker run --name mongodb -v ~/data:/data/db -d -p 27017:27017 mongo
```

# 9. install elk

```terminal
git clone https://github.com/deviantony/docker-elk.git
```
* `docker-elk` 폴더 안에 `elk/docker-compose.yml` 파일을 옮겨준다.
* `docker-elk/logstash/pipeline` 폴더 안에 `logstash.conf` 파일을 옮겨준다.
* change `.env` file

# 10. build backend server images && run container

* move to `backend/last-market`
* build backend images

```terminal
docker build -t last-market .
docker run --network host -d --name backend last-market
```

# 11. build front-end && move to nginx folder

* move to `/frontend`

```terminal
npm build run build
```

* move `build` folder to `opt/openvidu/html` folder