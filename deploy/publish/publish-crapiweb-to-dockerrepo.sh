set +x
aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 141020914838.dkr.ecr.us-west-2.amazonaws.com

./services/web/build-image.sh
./services/identity/build-image.sh

docker tag crapi-web:latest 141020914838.dkr.ecr.us-west-2.amazonaws.com/docker-dev/traceable/bot-protection-crapi-web:latest
docker tag crapi-identity:latest 141020914838.dkr.ecr.us-west-2.amazonaws.com/docker-dev/traceable/bot-protection-crapi-identity:latest

docker push 141020914838.dkr.ecr.us-west-2.amazonaws.com/docker-dev/traceable/bot-protection-crapi-web:latest
docker push 141020914838.dkr.ecr.us-west-2.amazonaws.com/docker-dev/traceable/bot-protection-crapi-identity:latest

