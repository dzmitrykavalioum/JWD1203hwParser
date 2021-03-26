package com.dzmitrykavalioum.utils;

import com.dzmitrykavalioum.entity.Node;
import com.dzmitrykavalioum.entity.NodeBuilder;

import java.io.*;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class XMLParser implements Closeable{
    //final String FILE_PATH = "notes.xml";
    final String FILE_PATH = "students2.xml";
    BufferedReader bufferedReader = null;
    Node node;



    public XMLParser() throws IOException {

       StringBuffer stringBuffer =  readXMLData(FILE_PATH);
       String formatData[]=  formattingData(stringBuffer);
       LinkedList<Node> nodes = createElements(formatData);

    }

    public StringBuffer readXMLData(String filePath) throws IOException {
        StringBuffer sbRawData = new StringBuffer("");
        File file = new File(filePath);
        FileReader reader = new FileReader(file);
        try {
            bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null) {
                sbRawData.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        finally {
            bufferedReader.close();
        }

        return sbRawData;
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
        LinkedList<Node> listOfNodes = new LinkedList<Node>();
        String openTag= "<\\p{Alnum}+[ \\p{Alnum}+=\"{1}\\p{Alnum}+\"{1}]*[>|/>]+";
        String closeTag = "</\\p{Alpha}+>";
        Pattern patternOpenTag = Pattern.compile(openTag);
        Pattern patternCloseTag = Pattern.compile(closeTag);

        for (int i = 0; i < formatData.length; ++i) {
            if ("".equals(formatData[i])) {
                continue;
            }
            if(patternOpenTag.matcher(formatData[i]).matches() && node == null) {
                node = new NodeBuilder(formatData[i]).build();
                listOfNodes.addLast(node);
            } else if (listOfNodes.size() >= 1) {
                if (patternOpenTag.matcher(formatData[i]).matches()) {
                    Node current = new NodeBuilder(formatData[i]).build();
                    listOfNodes.getLast().addNode(current);
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

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}
