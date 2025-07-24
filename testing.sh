#!/bin/bash

# List of target hosts (replace with your actual hostnames/IPs)
HOSTS=("host1" "host2" "host3" "host4")

# SSH credentials (consider using SSH keys instead of password)
USERNAME="your_username"
# If using password authentication:
PASSWORD="your_password"  # Not recommended - use SSH keys instead

# Directory and file details
BASE_PATH="/var/tmp"
FULL_PATH="$BASE_PATH/PPA/ISA"
FILE_PATH="$FULL_PATH/public.crt"

# Certificate content - REPLACE with your actual certificate
CERT_CONTENT="-----BEGIN CERTIFICATE-----
YOUR_CERTIFICATE_CONTENT_HERE
-----END CERTIFICATE-----"

# Check if sshpass is installed (for password authentication)
if ! command -v sshpass &> /dev/null; then
    echo "sshpass not found. Installing..."
    sudo yum install -y sshpass || sudo apt-get install -y sshpass
fi

for HOST in "${HOSTS[@]}"; do
    echo "Processing $HOST..."
    
    # Create directory structure and set permissions
    sshpass -p "$PASSWORD" ssh -o StrictHostKeyChecking=no "$USERNAME@$HOST" \
        "sudo mkdir -p $FULL_PATH && \
         sudo chmod -R 777 $BASE_PATH/PPA && \
         echo '$CERT_CONTENT' | sudo tee $FILE_PATH > /dev/null && \
         sudo chmod 777 $FILE_PATH"
    
    if [ $? -eq 0 ]; then
        echo "Successfully configured $HOST"
    else
        echo "Failed to configure $HOST"
    fi
done

echo "Certificate deployment completed."
