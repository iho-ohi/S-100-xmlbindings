# The Python S-125 XML Bindings Package

A python package implementing the XML bindings for the IALA S-125 data product
specification.

## Introduction

This Python package provides language bindings for the IALA S-125 data product, 
making it easier for developers to work with S-100 datasets. These bindings
allow developers to programmatically interact with S-100-compliant datasets
and data models, supporting the creation of applications that need to process,
validate, and manipulate S-125 data.

The S-125 belongs to the S-100 framework family, a standard developed by the
International Hydrographic Organization (IHO), is the framework for electronic
navigational charts, hydrographic data, and other maritime-related geospatial
information. These bindings are designed to facilitate integration of S-100
data into modern applications without the complexity of directly handling
S-100 specifications.

***!!!BE CAREFUL!!!***

This software is only intended for test purposes and should ***NOT BE USED IN ANY
PRODUCTION*** environment.

## S-125 XML - Python Classes Generation

First you need to install all the required packages using pip:

```bash
pip install xsdata xsdata[cli] iso639-lang pytest
```

To generate the package you can run the **xsdata** generation command using the
config provided in the repo.

```bash
xsdata generate ../../../specifications/s-125/1.0.0/S-125.xsd --package org.grad.enav.s125 --config .xsdata.xml --debug
```

Note that the configuration is fixed so that all the geneted classes are placed
under a single file. This handles the circular dependency issues. The you can
include generated **grad.s125** package into your software to parse and render
S-125 XML files.

## Packaging the Project

You can follow the great tutorial about packaging python projects
[here](https://packaging.python.org/en/latest/tutorials/packaging-projects/)

The metadata of the project can be found in the ***pyproject.toml*** file.
This defines the required libraries and corresponding versions, packaging
information, links to the README and licence files etc.

Then you can install PyPA's
[build](https://packaging.python.org/en/latest/key_projects/#build):

```bash
python -m pip install --upgrade build
```

Then generate the project package using the following command:

```bash
python -m build
```

Finally you can upload the package to a repository like PyPI using
[twine](https://packaging.python.org/en/latest/key_projects/#twine)

```bash
python -m pip install --upgrade twine
```

Note that you will also need an API token to upload the package, and you can
pass it to the following command:

```bash
python -m twine upload -r pypi dist/*
```

## License

Distributed under the MIT No Attribution (MIT-0) License. See
[LICENSE](../../../LICENSE) for more information.

## Contact

Nikolaos Vastardis - Nikolaos.Vastardis@gla-rad.org