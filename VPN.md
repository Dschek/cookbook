# VPN
## Install OpenVPN

### On server 

Install openvpn
- wget https://git.io/vpn -O openvpn-install.sh

Create sert
- bash openvpn-install.sh

Sert pwd 
-

### On client 

Install openvpn
- sudo  apt intall openvpn

Start openvpn 
- systemctl enable openvpn-client@.service service openvpn@client start &

Download  sert
- scp root@185.185.70.255:/root/phone.ovpn C:\pr\vpn

Mount directory
> found disk
- lsblk
> create dir
- mkdir sdb1
> mount dir to disk
- sudo mount /dev/sdb1 sdb1

Save sert
- cp sert.ovpn /etc/openvpn/client.conf

Start openvpn with sert 
- openvpn --client --config /etc/openvpn/client.conf & 

Auto start openvpn
- sudo systemctl enable openvpn-client@service