#
# Install the packages defined in the packages list
#

Package { ensure => "installed" }

{{#packages}}
package { "{{package}}": }
{{/packages}}