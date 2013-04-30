UAlerts Module Archetype
=======

An archetype that can be used to create new Maven modules in the UAlerts Server project.

## Usage

### Local Installation

To use the archetype, do the following:

1. Open a command line and navigate to the directory for this project.
2. Install the project by running <code>mvn clean install</code>.
3. Update your local archetype-catalog.xml by running <code>mvn archetype:crawl</code>.
4. In Eclipse, go the **Preferences** -> **Maven** -> **Archetypes**.
5. Click the **Add Local Catalog...** button.
6. For _Catalog File_, select your archetype-catalog.xml file (~/.m2/repository/archetype-catalog.xml for Mac/\*nix or &lt;User Home Directory&gt;/.m2/repository/archetype-catalog.xml for Windows)
7. Add a description of **Local Catalog**.
8. Hit the **OK** button.
9. Close the _Preferences_ screen by hitting **OK**.

### Creating project with archetype

In order to build the project, you will need to run from the command line.  The archetype uses properties, which Eclipse doesn't handle very gracefully.

1. Navigate to the location that you'd like to build the project.
2. Run the following command, replacing **${version}** with the current version of the archetype and **${group}** and **${artifact}** with the Group ID and Artifact ID you want for the new project:

   <code>mvn archetype:generate -DarchetypeGroupId=org.ualerts -DarchetypeArtifactId=org.ualerts.archetype.module -DarchetypeVersion=**${version}** -DgroupId=**${group}** -DartifactId=**${artifact}**</code>

3. Provide answers to the prompts for the various properties.
4. You're done!

### Importing Created Project into Eclipse

1. In Eclipse, go to **File** -> **Import...**.
2. Select the **Maven** -> **Existing Maven Projects**.
3. In the **Root Directory** field, navigate and select the folder that contains the project.
4. Ensure your project is checked in the list of Projects.
5. Continue through the rest of the wizard.