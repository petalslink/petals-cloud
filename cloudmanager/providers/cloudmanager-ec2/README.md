# Amazon EC2 provider

Provides connectivity and Karaf commands to Amazon EC2 compliant APIs.

## Install the Karaf feature

Once Apache karaf is launched:

    features:addurl mvn:org.ow2.petals.cloud/cloudmanager-ec2/1.0.0-SNAPSHOT/xml/features

Then install the ec2 feature

    features:install cloudmanager-ec2