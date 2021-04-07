package com.dzmitrykavalioum;

import com.dzmitrykavalioum.utils.XMLParser;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
       //XMLParser xmlParser =  new XMLParser("notes.xml");
       XMLParser xmlParser =  new XMLParser("students2.xml");
       xmlParser.parse();
    }
}
