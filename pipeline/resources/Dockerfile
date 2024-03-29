FROM docker.io/fedora:latest

ARG OC_VERSION=4
ARG VERSION=4.12
ARG USER=jenkins
ARG USER_UID=1001

ENV HOME=/home/${USER} \
    LANG=en_US.UTF-8 \
    LANGUAGE=en_US.UTF-8 \
    LC_COLLATE=C \
    LC_CTYPE=en_US.UTF-8 \
    LC_ALL=C \
    _BUILDAH_STARTED_IN_USERNS="" \
    BUILDAH_ISOLATION=chroot \
    STORAGE_DRIVER=vfs

USER root

ARG INSTALL_PKGS="bc gettext git git-lfs procps java-11-openjdk-headless lsof rsync tar unzip which zip bzip2 jq podman skopeo buildah"
RUN curl --create-dirs -fsSLo /usr/share/jenkins/agent.jar https://repo.jenkins-ci.org/public/org/jenkins-ci/main/remoting/${VERSION}/remoting-${VERSION}.jar && \
    curl -fsSLo /tmp/oc.tar.gz  https://mirror.openshift.com/pub/openshift-v${OC_VERSION}/x86_64/clients/ocp/stable/openshift-client-linux.tar.gz && \
    tar -C /usr/local/bin/ -xf /tmp/oc.tar.gz  'oc' 'kubectl' && \
    rm /tmp/oc.tar.gz && \
    dnf -y --refresh update && \
    dnf install -y --nodocs ${INSTALL_PKGS} && \
    dnf clean all  && \
    adduser -g 0 -u ${USER_UID} ${USER} && \
    chmod 755 /usr/share/${USER} && \
    chmod 644 /usr/share/${USER}/agent.jar && \
    ln -sf /usr/share/${USER}/agent.jar /usr/share/${USER}/slave.jar && \
    mkdir -p /home/${USER} && \
    mkdir /home/${USER}/.jenkins && \
    chown -R ${USER_UID}:0 /home/${USER} && \
    chmod -R 775 /home/${USER} && \
    chmod -R 775 /etc/alternatives && \
    chmod -R 775 /var/lib/alternatives && \
    chmod -R 775 /usr/lib/jvm && \
    chmod -R 775 /usr/bin && \
    chmod 775 /usr/share/man/man1 && \
    mkdir -p /var/lib/origin && \
    chmod 775 /var/lib/origin && \
    ln -s /usr/local/bin/jenkins-agent /usr/local/bin/jenkins-slave && \
    chmod u-s /usr/bin/new[gu]idmap && \
    setcap cap_setuid+eip /usr/bin/newuidmap && \
    setcap cap_setgid+eip /usr/bin/newgidmap && \
    rm -f /var/logs/*

WORKDIR /home/${USER}

ENTRYPOINT exec java -cp /usr/share/jenkins/agent.jar \
               hudson.remoting.jnlp.Main -headless -tunnel ${JENKINS_TUNNEL} \
               -url ${JENKINS_URL} -workDir ${JENKINS_AGENT_WORKDIR} \
               ${JENKINS_SECRET} ${JENKINS_AGENT_NAME}

USER ${USER_UID}