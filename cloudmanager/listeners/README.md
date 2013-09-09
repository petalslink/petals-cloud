# Cloud Manager Listeners

Listeners can be added dynamically to the Cloud Manager by using the OSGI dynamic deployment feature.
This folder contains a set of default listeners which may be deployed or not in the Cloud Manager.

## Installation

### Install as Features

Listeners may also be installed as features. Once packaged as feature, you can deploy the listener like:

    features:addurl mvn:org.ow2.petals.cloud/cloudmanager-listener-http/1.0.0-SNAPSHOT/xml/features
    features:install cloudmanager-listener-http

The previous command will install the HTTP listener.

### Shell Commands

To get the list of active listeners:

    listener:list

In order to test the listeners, you can call the test command:

    listener:test

This will send a random event to all the listeners.


