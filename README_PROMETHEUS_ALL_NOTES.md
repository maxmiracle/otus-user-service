```shell
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
```

```shell
helm install stack prometheus-community/kube-prometheus-stack -f prometheus.yaml
```

```shell
kubectl port-forward service/prometheus-operated 9090:9090
```

```shell
kubectl port-forward service/stack-grafana 9000:80
```
admin / prom-operator

```qprom
# Из материалов к занятию
http_server_requests_seconds_count
http_server_requests_seconds_count{uri=~"/api/a|/api/b"}[1m]

rate(http_server_requests_seconds_count{uri=~"/api/a|/api/b"}[1m])
sum (rate(http_server_requests_seconds_count{uri=~"/api/a|/api/b"}[1m]))
sum (rate(http_server_requests_seconds_count{uri=~"/api/a|/api/b"}[1m])) by (uri)

# квантиль 50
sum by (uri) (rate(http_server_requests_seconds_sum{uri=~"/api/.*"}[1m])) / sum by (uri) (rate(http_server_requests_seconds_count{uri=~"/api/.*"}[1m])) 

http_server_requests_seconds_bucket{uri=~"/api/a"}
rate(http_server_requests_seconds_bucket{uri=~"/api/a|/api/b"}[1m])


sum by(le) (http_server_requests_seconds_bucket{uri=~"/api/a|/api/b"})

sum by(le) (rate(http_server_requests_seconds_bucket{uri=~"/api/a|/api/b"}[1m]))
histogram_quantile(0.95, sum by(le) (rate(http_server_requests_seconds_bucket{uri=~"/api/a|/api/b"}[1m])))

histogram_quantile(0.95, sum by(le, uri) (rate(http_server_requests_seconds_bucket{uri=~"/api/.*"}[1m])))
```

```shell
kubectl get po -A -o wide
```


https://kubernetes.github.io/ingress-nginx/user-guide/monitoring/
```shell
helm upgrade ingress-nginx ingress-nginx \
--repo https://kubernetes.github.io/ingress-nginx \
--namespace ingress-nginx \
--set controller.metrics.enabled=true \
--set-string controller.podAnnotations."prometheus\.io/scrape"="true" \
--set-string controller.podAnnotations."prometheus\.io/port"="10254"
```

```shell
helm get values ingress-nginx --namespace ingress-nginx
```

---

Переделка, когда сначала руками устанавливаем nginx, чтобы его можно было настроить отправлять метрики в Prometheus

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts

helm repo update

/// из хабра
helm install kube-prometheus-stack prometheus-community/kube-prometheus-stack \
--create-namespace -n monitoring

/// из урока otus
helm install stack prometheus-community/kube-prometheus-stack -f prometheus.yaml


kubectl get pods -n monitoring

helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace \
  --set controller.metrics.enabled=true \
  --set controller.metrics.serviceMonitor.enabled=true \
  --set controller.metrics.serviceMonitor.additionalLabels.release="kube-prometheus-stack"

  kubectl port-forward service/prometheus-operated 9090:9090 -n monitoring

  kubectl port-forward service/kube-prometheus-stack-grafana 9000:80 -n monitoring

  kubectl port-forward service/ingress-nginx-controller-metrics 10254:10254 -n ingress-nginx

  10.98.48.199 

Метрика, которая не хочет показываться:
  nginx_ingress_controller_requests

Заново 2
minikube start

helm install stack prometheus-community/kube-prometheus-stack -f prometheus.yaml

helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace \
  --set controller.metrics.enabled=true \
  --set controller.metrics.serviceMonitor.enabled=true \
  --set controller.metrics.serviceMonitor.additionalLabels.release="kube-prometheus-stack" \
  --set-string controller.podAnnotations."prometheus\.io/scrape"="true" \
  --set-string controller.podAnnotations."prometheus\.io/port"="10254"

  kubectl port-forward service/prometheus-operated 9090:9090 

  ```shell
helm install my-release .
```

```shell
helm uninstall my-release

minikube addons enable ingress

minikube tunnel

kubectl port-forward service/stack-grafana 9000:80

stack-kube-prometheus-stac-prometheus

helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx \
  --set controller.metrics.enabled=true \
  --set controller.metrics.serviceMonitor.enabled=true


  helm upgrade ingress-nginx ingress-nginx/ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx \
  --set controller.metrics.enabled=true \
  --set controller.metrics.serviceMonitor.enabled=true \
  --set controller.metrics.serviceMonitor.additionalLabels.release="prometheus"


  helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
    --namespace ingress-nginx \
  --set controller.metrics.enabled=true \
  --set controller.metrics.serviceMonitor.enabled=true \
  --set controller.metrics.serviceMonitor.additionalLabels.release="stack"

  3 вариант

minikube start

helm repo add prometheus-community https://prometheus-community.github.io/helm-charts

  helm repo update

cat <<EOF | helm install kube-prometheus-stack prometheus-community/kube-prometheus-stack \
--create-namespace -n monitoring -f -
grafana:
  enabled: true
  adminPassword: "admin"
persistence:
  enabled: true
  accessModes: ["ReadWriteOnce"]
  size: 1Gi
ingress:
  enabled: true
  ingressClassName: nginx
  hosts:
    - grafana.localdev.me
EOF

kubectl get pods -n monitoring

helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace \
  --set controller.metrics.enabled=true \
  --set controller.metrics.serviceMonitor.enabled=true \
  --set controller.metrics.serviceMonitor.additionalLabels.release="kube-prometheus-stack"


kubectl port-forward service/kube-prometheus-stack-grafana 9000:80 -n monitoring

cd ~/otus/otus-user-service/user-chart

helm install my-release .

cat <<EOF | kubectl apply -f -
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: arch-ingress
spec:
  ingressClassName: nginx
  rules :
  - host: arch.homework
 
  defaultBackend:
    service:
      name: my-release-user-chart
      port:
        number: 80
EOF

kubectl port-forward -n ingress-nginx service/ingress-nginx-controller 8080:80

kubectl port-forward service/kube-prometheus-stack-grafana 9000:80 -n monitoring