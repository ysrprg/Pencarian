/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Yos Rio
 * 165314041
 */
public class Document implements Comparable<Document>{

    private int id;
    private String content;

    public Document() {
    }

    public Document(String content) {
        this.content = content;
    }

    public Document(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getListofTerm() {
        String value = this.getContent();
        value = value.replaceAll("[.,?!]", "");
        return value.split(" ");
    }

    public ArrayList<Posting> getListofPosting() {
        String[] tempString = getListofTerm();
        ArrayList<Posting> list = new ArrayList<>();
        for (int i = 0; i < tempString.length; i++) {
            if (i == 0) {
                Posting tempPosting = new Posting(tempString[0], this);
                list.add(tempPosting);
            } else {
                Collections.sort(list);
                Posting tempPosting = new Posting(tempString[i], this);
                int indexCari = Collections.binarySearch(list, tempPosting);
                if (indexCari < 0) {
                    list.add(tempPosting);
                } else {
                    int tempNumber = list.get(indexCari).getNumberOfTerm() + 1;
                    list.get(indexCari).setNumberOfTerm(tempNumber);
                }
            }
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public int compareTo(Document o) {
        return Integer.compare(this.id, o.id);
    }
    
    public void readFile(int idDoc, File file) throws FileNotFoundException, IOException{
        int len;
        char[] chr = new char[4096];
        final StringBuffer buffer = new StringBuffer();
        final FileReader reader = new FileReader(file);
        try {
            while ((len = reader.read(chr)) > 0) {
                buffer.append(chr, 0, len);
            }
        } finally {
            reader.close();
        }
        this.id = idDoc;
        this.content = buffer.toString();
    }
}
