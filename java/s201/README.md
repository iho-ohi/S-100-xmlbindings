# S-201 - GLA Research & Development
This repository contains some preliminary work on the IHO S-201 data product
describing Aids to Navigation (AtoN) information, performed by the UK's General
Lighthouse Authority Research and Development Directorate


*NO NOT USE THIS ON PRODUCTION*

The purpose of this repository is to just be used as a reference during the
S-201 data product standardization process.

## General Information
This repository is actually a Maven project. It currently contains a custom
definition of the S-201 data product, generated from the latest S-125 schema,
along with the associate S-100 structure definitions in the project's resources
folder.

[src/main/resources/xsd/S100/5.0.0/S100GML/20220620/s100gmlbase.xsd](src/main/resources/xsd/S100/5.0.0/S100GML/20220620/s100gmlbase.xsd)

[src/main/resources/xsd/S100/5.0.0/S100GML/20220620/S100_gmlProfile.xsd](src/main/resources/xsd/S100/5.0.0/S100GML/20220620/S100_gmlProfile.xsd)

[src/main/resources/xsd/S-201.xsd](src/main/resources/xsd/S-201.xsd)

Apart from the S-201 definition, this project can also be used as a Java 
dependency after it is built with Maven. Using JAXB it parses the S-201 XSD
schema definition and produces a list of Java objects to parse the S-201 data
in a Java service. Handy... right?

## Contributing
Pull requests are welcome. For major changes, please open an issue first to
discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
Distributed under the Apache License. See [LICENSE](./LICENSE) for more
information.

## Contact
Nikolaos Vastardis - Nikolaos.Vastardis@gla-rad.org

