set +x
aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/p9q5a9f9
./services/web/build-image.sh
./services/identity/build-image.sh
docker tag crapi/crapi-web:latest public.ecr.aws/p9q5a9f9/traceable/bot-protection-crapi-web:latest
docker tag crapi/crapi-identity:latest public.ecr.aws/p9q5a9f9/traceable/bot-protection-crapi-identity:latest

docker push public.ecr.aws/p9q5a9f9/traceable/bot-protection-crapi-web:latest
docker push public.ecr.aws/p9q5a9f9/traceable/bot-protection-crapi-identity:latest
