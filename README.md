# INF112 Maven template 
Simple skeleton with libgdx. 


## Known bugs


# Maven Setup
This project comes with a working Maven `pom.xml` file. You should be able to import it into Eclipse using *File → Import → Maven → Existing Maven Projects* (or *Check out Maven Projects from SCM* to do Git cloning as well). You can also build the project from the command line with `mvn compile` and test it with `mvn test`.

Pay attention to these folders:
* `src/main/java` – Java source files go here (as usual for Maven)
* `src/main/resources` – data files go here
* `src/test/java` – JUnit tests
* `target/classes` – compiled Java class files

You should probably edit the `pom.xml` and fill in details such as the project `name` and `artifactId`:


```xml

	< !-- FIXME - set group id -->
	<groupId>inf112.skeleton.app</groupId>
	< !-- FIXME - set artifact name -->
	<artifactId>mvn-app</artifactId>
	<version>1.0-SNAPSHOT</version>
	<packaging>jar</packaging>

	< !-- FIXME - set app name -->
	<name>mvn-app</name>
	< !-- FIXME change it to the project's website -->
	<url>http://www.example.com</url>
```

	
## Running
You can run the project from Eclipse, or with Maven using `mvn exec:java`. Change the main class by modifying the `main.class` setting in `pom.xml`:

```
		<main.class>inf112.skeleton.app.Main</main.class>
```

If you run `mvn package` you'll get a everything bundled up into a JAR file
* `target/*.jar` – your compiled project, packaged in a JAR file

#### POM snippets
If you're setting up / adding ANTLR4 to your own project, you can cut and paste these lines into your `pom.xml`file.

* You should make sure that both the parser generator and the runtime use the same version, so define the version number in `<properties>…</properties>`:

```xml
		<antlr4.version>4.8-1</antlr4.version>
```

* The ANTLR4 runtime is needed to run the compiled parser; add it in the `<depencencies>…</dependencies>` section:

```xml
<!-- https://mvnrepository.com/artifact/org.antlr/antlr4-runtime -->
<dependency>
	<groupId>org.antlr</groupId>
	<artifactId>antlr4-runtime</artifactId>
	<version>${antlr4.version}</version>
</dependency>
```

* The ANTLR4 maven plugin includes the ANTLR4 tool, and is needed to generate parser during compilation; add it to `<build><plugins>…</plugins></build>`:

```xml
<plugin>
	<groupId>org.antlr</groupId>
	<artifactId>antlr4-maven-plugin</artifactId>
	<version>${antlr4.version}</version>
	<executions>
		<execution>
			<goals>
				<goal>antlr4</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```