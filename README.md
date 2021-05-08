# Vehicle Electric Ontology (VEO) is an [OWL](https://www.w3.org/OWL/)-based representation of [Vehicle Electric Container](https://www.vda.de/de/services/Publikationen/vehicle-electric-container-vec.html) (VEC)

VEO offers many advantages versus VEC/XML-Schema:
- Semantic interoperability
- Ensures better traceability
- Linked data search
- Object-oriented and rule-based design
- Graph-based analysis, consistency check and visualisation
- Graph-based validation with [SHACL](https://www.w3.org/TR/shacl/)

You can open the ontology with [Protégé](https://protege.stanford.edu/)-Editor.

VEO has been developed within a research project at [FSG](https://www.uni-kassel.de/eecs/fachgebiete/fsg/startseite.html) by Oguzhan Balandi.

Files:
- __veo.owl__:  Is an ontology created based on XML Schema [VEC 1.1.1](https://ecad-wiki.prostep.org/specifications/vec/). The cardinalities and enumerations are not taken into account. They can be checked with SHACL in the file veo_instance_validation_with_shacl.ttl.
- __veo_1.2.0.owl__: Is an ontology created based on XML Schema [VEC 1.2.0](https://ecad-wiki.prostep.org/specifications/vec/).
- __kbl.ttl__: Is an ontology created based on XML Schema [KBL 2.3](https://ecad-wiki.prostep.org/specifications/kbl/).
- __ConverterVec2Veo.java__: A converter that converts VEC XML files into VEO-compliant RDF files. Is implemented in Java and requires [JDOM](http://www.jdom.org/) and [Apache Jena](https://jena.apache.org/) to run.
