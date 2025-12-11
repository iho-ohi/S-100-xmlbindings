# S-100 Catalog - GLA Research & Development
This repository contains some preliminary work on the IHO S-100 Catalog Exchange
Sets, performed by the UK's General Lighthouse Authority Research and 
Development Directorate ([GRAD](https://www.gla-rad.org/)).

*DO NOT USE THIS ON PRODUCTION*

The purpose of this repository is to just be used as a reference during the
S-100 standardization process.

## General Information
This repository is actually a Maven project. it contains a custom definition
of the IHO S-100 standard, version 5.0.0 and more importantly the catalog 
features from the following resources location.

[src/main/resources/xsd/S100/5.0.0/S100Catalog/20220705/S100_ExchangeCatalogue.xsd](src/main/resources/xsd/S100/5.0.0/S100Catalog/20220705/S100_ExchangeCatalogue.xsd)

Apart from the S-100 Catalog definition, this project can also be used as a Java 
dependency after it is built with Maven. Using JAXB it can parse the S-100
Catalog xsd definition and produce a list of Java objects to parse the S-100 
metadata in a Java service. Handy... right?

## Contributing
Pull requests are welcome. For major changes, please open an issue first to
discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
Distributed under the MIT No Attribution (MIT-0) License. See
[LICENSE](../../../LICENSE) for more information.

## Contact
Nikolaos Vastardis - Nikolaos.Vastardis@gla-rad.org

