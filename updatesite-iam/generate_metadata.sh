#!/bin/sh

ECLIPSE_DIR=~/apps/eclipse
UPDATE_SITE_DIR=~/dev/q4e/updatesite-iam

rm $UPDATE_SITE_DIR/artifacts.xml $UPDATE_SITE_DIR/content.xml

java -jar $ECLIPSE_DIR/plugins/org.eclipse.equinox.launcher_*.jar \
  -application org.eclipse.equinox.p2.publisher.UpdateSitePublisher \
  -metadataRepository file:$UPDATE_SITE_DIR \
  -artifactRepository file:$UPDATE_SITE_DIR \
  -source $UPDATE_SITE_DIR \
  -publishArtifacts false \
  -artifactRepositoryName "Eclipse IAM" \
  -metadataRepositoryName "Eclipse IAM"
