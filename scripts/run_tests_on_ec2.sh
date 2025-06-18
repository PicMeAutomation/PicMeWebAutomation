#!/bin/bash
exec > >(tee /var/log/ci-setup.log|logger -t user-data -s 2>/dev/console) 2>&1
set -e

sudo apt-get update -y
sudo apt-get install -y openjdk-17-jdk maven git unzip curl gnupg mailutils

wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install -y ./google-chrome-stable_current_amd64.deb || {
  echo "❌ Chrome install failed"; exit 1;
}

export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

cd /home/ubuntu
git clone https://github.com/PicMeAutomation/PicMeWebAutomation.git
cd PicMeWebAutomation

chmod +x mvnw || true
echo "Run_$(date +%Y%m%d_%H%M%S)" > build_run_count.txt

mvn clean test -DbaseUrl=https://picme.com/

REPORT_PATH=$(find . -nam_
