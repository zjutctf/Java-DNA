FROM ctfhub/base_web_tomcat_8u212

ADD docker/webapps.zip /tmp/
ADD docker/runssh.sh /tmp/

RUN echo "http://mirrors.aliyun.com/alpine/latest-stable/main/" > /etc/apk/repositories && \
    echo "http://mirrors.aliyun.com/alpine/latest-stable/community/" >> /etc/apk/repositories && \
    apk update && \
    apk add --no-cache openssh tzdata openrc && \ 
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    sed -i "s/#PermitRootLogin.*/PermitRootLogin yes/g" /etc/ssh/sshd_config && \
    ssh-keygen -t dsa -P "" -f /etc/ssh/ssh_host_dsa_key && \
    ssh-keygen -t rsa -P "" -f /etc/ssh/ssh_host_rsa_key && \
    ssh-keygen -t ecdsa -P "" -f /etc/ssh/ssh_host_ecdsa_key && \
    ssh-keygen -t ed25519 -P "" -f /etc/ssh/ssh_host_ed25519_key && \
    echo "root:admin" | chpasswd 
    
# 开放22端口
EXPOSE 22

RUN rm -rf /usr/local/tomcat/webapps/* && \
	cd webapps && \
	unzip /tmp/webapps.zip && \
	echo $FLAG > /flag.txt

CMD [ "/tmp/runssh.sh" ]