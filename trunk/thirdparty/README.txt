Q4E fork of Maven 2.1 (now 3.0)

to reduce the snapshot dependencies and make any changes necessary to adapt the embedder for use in Eclipse

The original Maven versions are committed in https://q4e.googlecode.com/svn/vendor/maven and merged into this maven folder when upgrades are neeeded.

You can run 
mvn source:jar install

and then go to the embedder Eclipse plugin and run 
mvn clean package -Peclipse-dev

solve any problems, conflicts,...