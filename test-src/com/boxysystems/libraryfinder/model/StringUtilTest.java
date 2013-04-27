package com.boxysystems.libraryfinder.model;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Jan 12, 2007
 * Time: 10:21:09 AM
 */
public class StringUtilTest extends AbstractLibraryFinderTest {

    public void testGetImportedClassFromLine_Simple() throws Exception{
        String className = StringUtil.getImportClassFromLine("import javax.mail.Address;");
        assertEquals("javax.mail.Address",className);
    }

    public void testGetImportedClassFromLine_SimpleWithStatic() throws Exception{
        String className = StringUtil.getImportClassFromLine("import static javax.mail.Address;");
        assertEquals("javax.mail.Address",className);
    }

    public void testGetImportedClassFromLine_SimpleWithSpaces() throws Exception{
        String className = StringUtil.getImportClassFromLine("    import     javax.mail.Address ;   ");
        assertEquals("javax.mail.Address",className);
    }

    public void testGetImportedClassFromLine_Invalid() throws Exception{
        String className = StringUtil.getImportClassFromLine("class javax.mail.Address;");
        assertNull(className);
    }

    public void testGetImportedClassFromLine_WithStarImports() throws Exception{
        String className = StringUtil.getImportClassFromLine("import javax.mail.*;");
        assertEquals("javax.mail.*",className);
    }
}
