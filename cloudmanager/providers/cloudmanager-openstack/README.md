# OpenStack Cloud Manager module

## Install the Karaf Feature

Once Apache karaf is launched:

    features:addurl mvn:org.ow2.petals.cloud/cloudmanager-openstack/1.0.0-SNAPSHOT/xml/features

Then install the openstack feature

    features:install cloudmanager-openstack

You should see the Openstack connector by running the iaas/providers command

    iaas:providers
    Providers list :
     - openstack(v1.0)
     - aws-ec2(v1.0)

## Notes

- Module contains sources from Woorea OpenStack Connector (copy/paste) due to classloader issues in karaf with the wrapper feature (dep is not a bundle nor a valid jar...).
