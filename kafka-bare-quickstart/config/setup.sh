# kubectl create namespace kafka
# kubectl -n kafka create -f ./config/kafka-cluster.yaml
# kubectl wait kafka --all --timeout=-1s --for=condition=Ready -n kafka
# kubectl create namespace knative-kafka-hello-quickstart

oc project knative-kafka-hello-quickstart
mvnw install -Pnative

kubectl apply -f ./config/knative-service.yaml
# kn service create kafka-quickstart --image image-registry.openshift-image-registry.svc:5000/knative-kafka-hello-quickstart/kafka-bare-quickstart:1.0-SNAPSHOT

