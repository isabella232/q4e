#!/bin/sh

echo '<?xml version="1.0" encoding="UTF-8"?>
<classpath>'

for f in `find lib -type f | grep -v .svn | sed s/\.jar//` 
  do echo '	<classpathentry exported="true" kind="lib" path="'$f'.jar" sourcepath="'$f'-sources.jar"/>'
done

echo '	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER"/>
	<classpathentry kind="con" path="org.eclipse.pde.core.requiredPlugins"/>
	<classpathentry kind="output" path="target/classes"/>
</classpath>
'