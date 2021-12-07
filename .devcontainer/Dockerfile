ARG VARIANT="buster"
FROM mcr.microsoft.com/vscode/devcontainers/java:0-8-${VARIANT}

ARG GRADLE_VERSION=""
RUN su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""
