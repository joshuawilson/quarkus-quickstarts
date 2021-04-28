kubectl create namespace kafka
kubectl -n kafka create -f ./config/kafka-cluster.yaml
kubectl wait kafka --all --timeout=-1s --for=condition=Ready -n kafka
kubectl create namespace knative-kafka-quarkus-example

# kubectl apply -f ./config/03-topic.yaml
# kn channel create my-data-stream --namespace knative-kafka-quarkus-example
# kn channel create prices --namespace knative-kafka-quarkus-example
# kn channel create generated-price --namespace knative-kafka-quarkus-example
# kn channel create my-data-stream --namespace kafka
# kn channel create prices --namespace kafka
# kn channel create generated-price --namespace kafka

oc project knative-kafka-quarkus-example
mvnw install -Pnative

kubectl apply -f ./config/02b-knative-service.yaml

# kubectl apply -f ./config/03-topic.yaml
# kn service create kafka-quickstart --image image-registry.openshift-image-registry.svc:5000/knative-kafka-quarkus-example/knative-kafka-quarkus-example:1.0.0-SNAPSHOT
# kubectl apply -f ./config/04-source.yaml
