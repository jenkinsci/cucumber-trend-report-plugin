package org.jenkinsci.plugins.cucumbertrendsreport.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class CommonTest {
    private String path;
    private File dir1;
    private File dir2;

    @Before
    public void setUp() throws Exception {
        this.path = Common.class.getClass().getResource("/directory/").getPath();
        this.dir1 = new File(this.path,"dir1");
        this.dir2 = new File(this.path,"dir2");
    }

    @After
    public void tearDown() throws Exception {
        File fileToDelete = new File(this.dir1,"newFile.txt");
        if(fileToDelete.exists()){
            fileToDelete.delete();
        }
    }

    @Test
    public void testGetSubFolderWithValidPath() throws Exception {
        List<String> subFolders = Common.getSubFolder(this.path);
        assertEquals(subFolders.size(), 2);
        assertTrue(subFolders.contains(this.dir1.toString()));
        assertTrue(subFolders.contains(this.dir2.toString()));
    }

    @Test
    public void testGetSubFolderWithInValidPath() throws Exception {
        List<String> subFolders = Common.getSubFolder("A wrong path");
        assertTrue(subFolders.isEmpty());
    }

    @Test
    public void testCreateTextFile() throws Exception {
        File newTextFile = new File(this.dir2,"newFile.txt");
        String content = "This is a new file";
        Common.createTextFile(newTextFile,content);

        String readFile = Common.getText(newTextFile.toString());
        assertEquals(readFile,content);
    }

    @Test
    public void testGetCurrentDateTime() throws Exception {

    }

    @Test
    public void testGetText() throws Exception {
        File textFile = new File(this.dir1,"text1.txt");
        String text = Common.getText(textFile.toString());
        assertEquals(text, "This is a text");
    }

    @Test
    public void testGetTextWithInvalidPath() throws Exception {
        File textFile = new File("wrongFile.txt");
        String text = Common.getText(textFile.toString());
        assertEquals(text, "");
    }
}