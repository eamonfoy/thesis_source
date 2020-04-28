sudo mkdir -p /mnt/vagrant-kubernetes
sudo mkdir -p /mnt/vagrant-kubernetes/data
sudo mkdir -p /mnt/vagrant-kubernetes/aodb
sudo mkdir -p /mnt/vagrant-kubernetes/flight
sudo mkdir -p /mnt/vagrant-kubernetes/weather
sudo mkdir -p /mnt/vagrant-kubernetes/elastic
sudo chown nobody:nogroup /mnt/vagrant-kubernetes
sudo chmod 777 /mnt/vagrant-kubernetes

sudo exportfs -a
sudo systemctl restart nfs-kernel-server    
sudo exportfs -v
