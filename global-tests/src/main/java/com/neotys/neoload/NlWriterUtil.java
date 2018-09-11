package com.neotys.neoload;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.neotys.neoload.model.repository.Container;
import com.neotys.neoload.model.writers.neoload.ContainerWriter;

public class NlWriterUtil {

	public static String write(final Container model) throws Exception {

		final DocumentBuilderFactory repositoryDocFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder repositoryDocBuilder;
		repositoryDocBuilder = repositoryDocFactory.newDocumentBuilder();
		final Document document = repositoryDocBuilder.newDocument();
		final Element element = document.createElement("root");
		document.appendChild(element);
		ContainerWriter.of(model, "action-container").writeXML(document, element, getTmpFile());
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.VERSION, "1.1");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		final DOMSource repositorySource = new DOMSource(document);
		final File result = File.createTempFile(LrToNlTest.class.toString(), "");
		StreamResult repositoryStream = new StreamResult(result);
		transformer.transform(repositorySource, repositoryStream);
		final String fullXML = new String(Files.readAllBytes(Paths.get(result.getAbsolutePath())));		
		if(fullXML.contains("</action-container>")){
			return fullXML.substring(fullXML.indexOf("</action-container>") + "</action-container>".length() + 2, fullXML.indexOf("</root>") -2);
		}
		return fullXML.substring(fullXML.indexOf("weightsEnabled=\"false\"/>") + "weightsEnabled=\"false\"/>".length() + 2, fullXML.indexOf("</root>"));			
	}

	private static String getTmpFile() {
		try {
			final File temp = File.createTempFile(LrToNlTest.class.toString(), "");
			temp.deleteOnExit();
			return temp.getAbsolutePath();
		} catch (Exception e) {
		}
		return null;
	}
}
