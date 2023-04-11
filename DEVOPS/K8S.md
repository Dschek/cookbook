# INSTALL CRIO
SET UBUNTU VERSION
```
OS=xUbuntu_22.04
```
SET CRIO VERSION
```
CRIO_VERSION=1.26
```
ADD CRIO REPOSITORY 
```
echo "deb https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable/$OS/ /"|sudo tee /etc/apt/sources.list.d/devel:kubic:libcontainers:stable.list
```
```
echo "deb http://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable:/cri-o:/$CRIO_VERSION/$OS/ /"|sudo tee /etc/apt/sources.list.d/devel:kubic:libcontainers:stable:cri-o:$CRIO_VERSION.list
```
DOWNLOAD CRIO 
```
curl -L https://download.opensuse.org/repositories/devel:kubic:libcontainers:stable:cri-o:$CRIO_VERSION/$OS/Release.key | sudo apt-key add -
```
```
curl -L https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable/$OS/Release.key | sudo apt-key add -
```
UPDATE PACKEGE 
```
sudo apt update
```
INSTALL CRIO
```
sudo apt install cri-o cri-o-runc
```
AUTO START CRIO
```
sudo systemctl enable crio.service
```
START CRIO
```
sudo systemctl start crio.service
```
INSTALL CRIO TOOLS
```
sudo apt install cri-tools
```
# CONFIGURE HOSTS ON MASTER AND WORKER
## CHANGE HOSTS FILE
OPEN HOST FILE
```
sudo nano /etc/hosts
```
CHANGE 
- MASTER
```
IP_ADDRESS kube-master 
```
- WORKER
```
IP_ADDRESS kube-worker
```
SET HOSTNAME
- MASTER
```
sudo hostnamectl set-hostname kube-master
```
- WORKER
```
sudo hostnamectl set-hostname kube-worker
```
# INSTALL K8S
UPDATE PACKAGE
```
sudo apt update && sudo apt upgrade -y
```
INSTALL APT-TRANSPORT-HTTPS
```
sudo apt install curl apt-transport-https -y
```
SET GPG KEY 
```
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
```
ADD  TO  REPO
```
echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" | sudo tee /etc/apt/sources.list.d/kubernetes.list
```
INSTALL PACKAGE
```
sudo apt update
```
INSTALL KUBELET KUBEADM KUBECTL
```
sudo apt -y install vim git curl wget kubelet kubeadm kubectl
```
DISABLE AUTO UPDATE 
```
sudo apt-mark hold kubelet kubeadm kubectl
```
AUTO START KUBELET 
```
sudo systemctl enable --now kubelet
```
DISABLE ADD SWAP 
```
sudo swapoff -a
```
# CONFIG 
ENABLE EVERLAY
```
sudo modprobe overlay
```
ENABLE BR_NETFILTER
```
sudo modprobe br_netfilter
```
## CONFIG SYSCTL
OPEN K8S CONFIG
```
sudo nano /etc/sysctl.d/kubernetes.conf
```
SET PARAM
```
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
net.ipv4.ip_forward = 1
```
RESTART SYSCTL
```
sudo sysctl --system
```
# START K8S
## MASTER
PULL IMAGE 
```
sudo kubeadm config images pull
```
INIT
```
sudo kubeadm init --pod-network-cidr=10.8.0.2/16
```
- SAVE COMAND
- OR GET COMAND 
```
sudo kubeadm token create --print-join-command
```
## WORKER
- START SAVE COMAND
## MASTER
ADD KUBE DIR
```
mkdir -p $HOME/.kube
```
COPY CONFIG
```
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
```
CHANGE CHOWN
```
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```
INSTALL FLANNEL NEWORK AND CONFIGURE
```
kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
```
### OR IF USE NOT DEFAULT MASTER NODE HOST
DOWNLOAD CONFIG, CHANGE HOST
```
https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
```
INSTALL FLANNEL NEWORK AND CONFIGURE
```
kubectl apply -f kube-flannel.yml
```
CHECK NETWORK 
```
kubectl get pods --all-namespaces
```
SET NODE WORKER ROLE
```
sudo kubectl label node kube-worker node-role.kubernetes.io/worker=worker
```
CHECK NODE AND NODE ROLE
```
kubectl get nodes
```
