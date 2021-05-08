package veo.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/*
 *	A converter that converts VEC XML files into VEO-compliant RDF files.
 *
 * @author Oguzhan Balandi
 * @version 1.0
 */
public class ConverterVec2Veo {
	
	/*
	 * OntModel
	 */
	static OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);

	/*
	 * Main-function
	 */
	static void run(String inputFile, String outputFile, String prefix, String namespace) throws FileNotFoundException{
		
		String VEO_NS = "http://www.vec-ontology.org/";
		String instance_NS = namespace;

		//set prefix
		model.setNsPrefix("veo", "http://www.vec-ontology.org/");
		model.setNsPrefix(prefix, namespace);

		//read VEC-XML file
		Document vec_doc = readXML(inputFile);
		Element root = vec_doc.getRootElement();

		//run recursive function
		recursiveTyper(root, VEO_NS, instance_NS);

		//Write
		FileOutputStream out = new FileOutputStream(outputFile);
		RDFDataMgr.write(out, model, Lang.RDFXML);
	}

	/*
	 * Sub-function
	 */
	static void recursiveTyper(Element element, String VEO_NS, String instance_NS){

		String name = element.getName();

		if(element.getChildren().size() > 0){	

			Individual ind  = model.createIndividual(instance_NS + element.getAttributeValue("id"),	model.getResource(VEO_NS + element.getName()));  

			if(element.getParentElement() != null){
				Resource parent = model.getResource(instance_NS + element.getParentElement().getAttributeValue("id"));

				parent.addProperty(model.getProperty(VEO_NS + name.substring(0,1).toLowerCase() + name.substring(1,name.length())), ind);
			}

			for(Element child : element.getChildren()){
				recursiveTyper(child, VEO_NS, instance_NS);
			}

		}

		else{

			Resource parent = model.getResource(instance_NS + element.getParentElement().getAttributeValue("id"));
	
			if(element.getValue().startsWith("id")){
				
				parent.addProperty(model.getProperty(VEO_NS + name.substring(0,1).toLowerCase() + name.substring(1,name.length()) ), 
						model.getResource(instance_NS + element.getValue()));

			}else{

				parent.addLiteral(model.getProperty(VEO_NS + name.substring(0,1).toLowerCase() + name.substring(1,name.length()) ), 
						element.getValue());
			}
		}

	}

	/**
	 * XML Reader
	 * @param fileDirectory
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static Document readXML(String fileDirectory) throws FileNotFoundException{

		//Read counter file
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(fileDirectory);

		//Files
		Document document = null;

		try {

			document = (Document) builder.build(xmlFile);
			document.getRootElement();

		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}

		return document;
	}
	
	/*
	 * Example
	 */
	public static void main(String[] args) throws FileNotFoundException {

		/*
		 * Input file: https://ecad-wiki.prostep.org/sample/2018-12-27-volkswagen-vobesng-old-beetle/oldbeetle_vec113.vec
		 */
		
		String inputFile = ".../oldbeetle_vec113.vec";
		String outputFile = ".../oldbeetle_vec113.rdf";
		String prefix = "ex";
		String namespace = "https://ecad-wiki.prostep.org/sample/2018-12-27-volkswagen-vobesng-old-beetle/";
	
		run(inputFile, outputFile, prefix, namespace);

	}

}
