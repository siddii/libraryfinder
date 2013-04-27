package com.boxysystems.libraryfinder.view.intellij;

import com.boxysystems.libraryfinder.model.AbstractLibraryFinderTest;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Sep 26, 2006
 * Time: 10:43:04 AM
 */
public class LibraryFinderHistoryTest extends AbstractLibraryFinderTest {

    public void testWriteExternal() throws WriteExternalException, InterruptedException {
        LibraryFinderHistory history = new LibraryFinderHistory();
        history.addDir("dir 1");
        Thread.sleep(100);
        history.addDir("dir 2");
        Thread.sleep(100);
        history.addDir("dir 3");
        Thread.sleep(100);
        history.addDir("dir 4");

        assertEquals(4, history.getDirs().size());

        history.addResource("resource 1");
        Thread.sleep(100);
        history.addResource("resource 2");
        Thread.sleep(100);
        history.addResource("resource 3");
        Thread.sleep(100);

        assertEquals(3, history.getResources().size());

        Element rootElement = new Element("root");
        history.writeExternal(rootElement);
        XMLOutputter xmlOutputter = new XMLOutputter();
        String xmlString = xmlOutputter.outputString(rootElement);
        assertTrue(xmlString.contains("dir 1"));
        assertTrue(xmlString.contains("dir 2"));
        assertTrue(xmlString.contains("dir 3"));
        assertTrue(xmlString.contains("dir 4"));

        assertTrue(xmlString.contains("resource 1"));
        assertTrue(xmlString.contains("resource 2"));
        assertTrue(xmlString.contains("resource 3"));
    }

    public void testReadExternal() throws WriteExternalException, InvalidDataException {
        Element rootElement = new Element("root");
        Element historyElement = new Element(LibraryFinderHistory.HISTORY_TAG);

        Element dirsElement = new Element(LibraryFinderHistory.DIRS_TAG);

        Element element = new Element(LibraryFinderHistory.ELEMENT_TAG);
        element.setAttribute(LibraryFinderHistory.VALUE_ATTRIB, "dir 1");

        dirsElement.addContent(element);

        element = new Element(LibraryFinderHistory.ELEMENT_TAG);
        element.setAttribute(LibraryFinderHistory.VALUE_ATTRIB, "dir 2");

        dirsElement.addContent(element);

        historyElement.addContent(dirsElement);

        Element resourceElement = new Element(LibraryFinderHistory.RESOURCES_TAG);

        element = new Element(LibraryFinderHistory.ELEMENT_TAG);
        element.setAttribute(LibraryFinderHistory.VALUE_ATTRIB, "resource 1");

        resourceElement.addContent(element);

        historyElement.addContent(resourceElement);

        rootElement.addContent(historyElement);

        LibraryFinderHistory history = new LibraryFinderHistory();
        history.readExternal(rootElement);
        List<String> dirs = history.getDirs();
        assertNotNull(dirs);
        assertEquals(2, dirs.size());
        assertTrue(dirs.contains("dir 1"));
        assertTrue(dirs.contains("dir 2"));

        List<String> resources = history.getResources();
        assertNotNull(resources);
        assertEquals(1, resources.size());
        assertTrue(resources.contains("resource 1"));
    }

    public void testReadAndWrite() throws WriteExternalException, InterruptedException, IOException, ParserConfigurationException, SAXException, InvalidDataException {
        LibraryFinderHistory history = new LibraryFinderHistory();
        history.addDir("dir 1");
        Thread.sleep(100);
        history.addDir("dir 2");
        Thread.sleep(100);
        history.addDir("dir 3");
        Thread.sleep(100);
        history.addDir("dir 4");

        assertEquals(4, history.getDirs().size());

        history.addResource("resource 1");
        Thread.sleep(100);
        history.addResource("resource 2");
        Thread.sleep(100);
        history.addResource("resource 3");
        Thread.sleep(100);

        assertEquals(3, history.getResources().size());

        Element rootElement = new Element("root");
        history.writeExternal(rootElement);
        XMLOutputter xmlOutputter = new XMLOutputter();
        String xmlString = xmlOutputter.outputString(rootElement);

        Document w3cDoc = stringToDocument(xmlString);
        DOMBuilder domBuilder = new DOMBuilder();
        org.jdom.Document jdomDoc = domBuilder.build(w3cDoc);

        LibraryFinderHistory builtHistory = new LibraryFinderHistory();
        builtHistory.readExternal(jdomDoc.getRootElement());
        List<String> dirList = builtHistory.getDirs();
        assertEquals("dir 4", dirList.get(0));
        assertEquals("dir 3", dirList.get(1));
        assertEquals("dir 2", dirList.get(2));
        assertEquals("dir 1", dirList.get(3));

        List<String> resourcesList = builtHistory.getResources();
        assertEquals("resource 3", resourcesList.get(0));
        assertEquals("resource 2", resourcesList.get(1));
        assertEquals("resource 1", resourcesList.get(2));
    }


    public static Document stringToDocument(String xmlSource)
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }
}
