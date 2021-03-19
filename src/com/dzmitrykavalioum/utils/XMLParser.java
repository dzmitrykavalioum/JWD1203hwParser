package com.dzmitrykavalioum.utils;

import com.dzmitrykavalioum.entity.Node;
import com.dzmitrykavalioum.entity.NodeBuilder;

import java.io.*;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class XMLParser {
    //final String FILE_PATH = "notes.xml";
    final String FILE_PATH = "students2.xml";

    Node node;



    public XMLParser() throws IOException {

       StringBuffer stringBuffer =  readXMLData(FILE_PATH);
       String formatData[]=  formattingData(stringBuffer);
       LinkedList<Node> nodes =  createElements(formatData);
  //     printNodes(nodes);
    }

    public StringBuffer readXMLData(String filePath) throws IOException {
        StringBuffer rawData = new StringBuffer("");
        try {
            File file = new File(filePath);
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            String line = buffer.readLine();
            while (line != null) {
                rawData.append(line);
                line = buffer.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return rawData;
    }

    public String[] formattingData(StringBuffer data) {
        String [] formatData = data.toString()
                .replace(">", ">\n")
                .replace("</", "\n</")
                .split("\n");
        for (int i = 0; i < formatData.length; ++i) {
            formatData[i] = formatData[i].trim();
        }
        return formatData;
    }

    public LinkedList<Node> createElements(String [] formatData) {
        LinkedList<Node> listOfNodes = new LinkedList<>();
        String openTagRegex= "<\\p{Alnum}+[ \\p{Alnum}+=\"{1}\\p{Alnum}+\"{1}]*[>|/>]+";
        String closeTagRegex = "</\\p{Alpha}+>";
        Pattern patternCloseTag = Pattern.compile(closeTagRegex);
        Pattern pattern = Pattern.compile(openTagRegex);
        for (int i = 0; i < formatData.length; ++i) {
            if ("".equals(formatData[i])) {
                continue;
            }
            if(pattern.matcher(formatData[i]).matches() && node == null) {
                node = new NodeBuilder(formatData[i]).build();
                listOfNodes.addLast(node);
            } else if (listOfNodes.size() >= 1) {
                if (pattern.matcher(formatData[i]).matches()) {
                    Node current = new NodeBuilder(formatData[i]).build();
                    listOfNodes.getLast().setNode(current);
                    listOfNodes.addLast(current);
                } else if (patternCloseTag.matcher(formatData[i]).matches()) {
                    listOfNodes.removeLast();
                } else {
                    listOfNodes.getLast().setContent(formatData[i]);

                }
            }
        }
        return listOfNodes;

    }

    public void printNodes(LinkedList<Node> nodes){
        for (Node node:nodes){
            System.out.println(node.toString());
        }
    }
}
