# Python bindings

There are several libraries available for Python XML bindings.

* [PyXB](https://pabigot.github.io/pyxb/) - support ends at Python 3.7, most similar to JaXB
* [PyXB-X](https://github.com/renalreg/PyXB-X) - fork of PyXB that maintains support for PyXB
* [xsdata](https://github.com/tefra/xsdata) - regularly updated and tested against W3C XML Schema 1.1 test suite

## PyXB / PyXB-X

**Not recommended for use**

The experience so far is that PyXB has significant issues with S-100 schema files and especially the reuse of the GML namespace causing duplicate classes as PyXB has inbuilt knowledge of GML schemas. There are also other issues in the use that may be able to be resolved by manually editing the generated bindings.

The support for PyXB-X is based on the primary author's needs and it is unclear how long that support will last.

## xsdata

xsdata has been proven to work with S-100 schema files and has been tested with the proposed 2.0.0 version of the S-421 schema. However, the following caveats must be taken into account. The default command to generate bindings is

```
xsdata generate [SCHEMA FILE] [OUTPUT PACKAGE NAME]
```

However, this command has the following issues:

* Generates very complicated Python package names due to the requirements in Python package names
* Generates circular dependencies between the S-100 `s100bmlbase.xsd` and `S100_gmlProfile.xsd` (at least in version 5.2.0)

These issues can be resolved by adding the parameter `--structure-style` with the value `single-package` but this generates larger files. The benefit however is that the resulting bindings for a given S-100 product is then a standalone file. Despite this, the following command is currently the preferred option to generate S-100 bindings in Python:

```
xsdata generate [SCHEMA FILE] --sturcture-style single-package [OUTPUT PACKAGE NAME]
```