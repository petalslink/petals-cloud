# Cloud Manager DSB

Provides Petals DSB configuration hooks.

## Karaf Shell installation

Add the feature URL

    features:addurl mvn:org.ow2.petals.cloud/cloudmanager-dsb/1.0.0-SNAPSHOT/xml/features

Then install the feature

    features:install cloudmanager-dsb

Once installed, the DSB provider should appear in the deployment providers list

    paas:providers

Will return

    Deployment Providers :
     - dsb-masterslave
     - xxx

You can now create DSB PaaS from the Karaf Shell like

    paas:create --iaas ow2stack --type dsb-masterslave --size 100

This will create a DSB PaaS in Master/Slave mode on the ow2stack IaaS using 100 VMs.