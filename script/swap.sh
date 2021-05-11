#!/bin/sh
# When apply the blue green deployment, run this file to switch between blue and green
testing_now=$(ls -l ./ | grep testing)
if [[ "$testing_now" == *green ]]
then
  inactive="blue"
  active="green"
else
  inactive="green"
  active="blue"
fi
#remove current links
rm ./available
rm ./testing
rm -f /etc/nginx/sites-enabled/canh-labs.com
#create new links with the active/inactive reversed
ln -s ./$active ./available
ln -s ./$inactive ./testing
ln -s /opt/canh-labs/shorten/$active/canh-labs.com /etc/nginx/sites-enabled/canh-labs.com
#reload the http server
service nginx reload
echo swap completed $active is now available

