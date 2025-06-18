#!/bin/bash
exec > >(tee /var/log/ci-setup.log | logger -t user-data -s 2>/dev/console) 2>&1
set -e

# Update packages
sudo apt-get update -y

# Install required tools
sudo apt-get install -y openjdk-17-jdk maven git unzip curl gnupg mailutils

# Install Chrome
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt install -y ./google-chrome-stable_current_amd64.deb || {
  echo "❌ Chrome install failed"; exit 1;
}

# Set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH

# Clone your GitHub repo
cd /home/ubuntu
git clone https://github.com/PicMeAutomation/PicMeWebAutomation.git
cd PicMeWebAutomation

# Set executable permission on scripts
chmod +x mvnw || true

# Set report path dynamically (if needed by your framework)
echo "Run_$(date +%Y%m%d_%H%M%S)" > build_run_count.txt

# Run tests
mvn clean test -DbaseUrl=https://picme.com/

# Send Report.html via email if exists
REPORT_PATH=$(find . -name Report.html | head -n 1)
if [ -f "$REPORT_PATH" ]; then
    echo "Test run completed. Report attached." | mail -s "Test Report" -a "$REPORT_PATH" qatest122419@gmail.com
else
    echo "❌ Report.html not found. No email sent."
fi

# Shutdown instance
sudo shutdown -h now
