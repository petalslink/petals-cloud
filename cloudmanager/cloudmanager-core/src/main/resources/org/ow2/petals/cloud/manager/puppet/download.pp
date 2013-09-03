#
# Download a set of files with puppet
#

package { "wget":
  ensure => "installed"
}

define download(
  $uri,
  $timeout = 300
) {
  exec { $name:
    command => "/usr/bin/wget --no-check-certificate -nv ${uri} -O ${name}",
    timeout => $timeout,
    logoutput => true,
    tries => 3,
    try_sleep => 5,
    require => Package["wget"]
  }
}

{{#uris}}
download {"{{destination}}":
  uri => "{{source}}"
}
{{/uris}}