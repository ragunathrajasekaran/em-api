ARG VARIANT="buster"
FROM mcr.microsoft.com/vscode/devcontainers/java:0-8-${VARIANT}

ARG USERNAME=vscode
RUN mkdir -p /home/$USERNAME/.vscode-server/extensions \
/home/$USERNAME/.vscode-server-insiders/extensions \
&& chown -R $USERNAME \
/home/$USERNAME/.vscode-server \
/home/$USERNAME/.vscode-server-insiders

ARG GRADLE_VERSION=""
RUN su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""
