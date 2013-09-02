# Cloud Manager

Manage distributed cloud infrastructures and applications the easy way.
The Petals Cloud Manager allows to deploy distributed applications on heterogeneous infrastructures by hiding the IaaS APIs complexity.

## Howto

In the following parts, we assume that we are running the cloud manager from the command line.

### Manage IaaS

#### IaaS Accounts

One can register multiple Cloud accounts in the platform in order to deploy applications to one or more IaaS.
What about deploying a part of your application on EC2 and the other part(s) on some OpenStack platforms? In this case you will have to register your credentials once, the manager will do the rest (almost).

    iaas:account

You can get your accounts list by running

    iaas:accounts

And check which are the available providers in the platform

    iaas:providers

will give you this output if openstack and ec2 connectors are available

    karaf@root> iaas:providers
    Providers list :
     - openstack(v1.0)
     - aws-ec2(v1.0)

Once registered, accounts can be used by giving their name/id to other commands. For example, by using the openstack provider, you can get the list of OpenStack servers for a specified account:

    karaf@root> openstack:servers --account=myopenstackaccount

### Manage PaaS

The Cloud manager provides a simple API to register, deploy and manage PaaS. This means that you can define multi-IaaS deployments for your application and get back your PaaS.
This is possible by defining actions and patterns that will generate valid calls according to the IaaS deployment targets.

For example, you can describe your distributed software requirements, the deployment IaaS(s) (if needed, we can also imagine that the controller will deploy your distributed application autoatically on the most suitable IaaSs) and the cloud manager will do the rest.

#### PaaS definition

TODO


