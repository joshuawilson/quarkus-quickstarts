kubectl create namespace kafka
kubectl -n kafka create -f ./config/kafka-cluster.yaml
kubectl wait kafka --all --timeout=-1s --for=condition=Ready -n kafka

kubectl create namespace knative-kafka-quarkus-example

oc project knative-kafka-quarkus-example
# kubectl apply -f ./config/knative-eventing.yaml
kubectl apply -f ./config/knative-sources.yaml

mvnw install -Pnative

kubectl apply -f ./config/knative-service.yaml
