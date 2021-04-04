package com.dzmitrykavalioum.utils;

import com.dzmitrykavalioum.entity.Node;
import com.dzmitrykavalioum.entity.NodeBuilder;
import com.dzmitrykavalioum.exception.ParserException;
import com.dzmitrykavalioum.implementation.Parser;

import java.io.*;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class XMLParser implements Closeable, Parser {
    private final String openTag = "<[a-zA-Z]+[\\s*[a-zA-Z:]*=*\\\"{1}[a-z_]*\\\"{1}]*>+";
    private final  String closeTag = "</[a-zA-Z]+>";
    private String filePath;
    final String FILE_PATH = "notes.xml";
    //final String FILE_PATH = "students2.xml";
    BufferedReader bufferedReader = null;
    Node node;

    public XMLParser() {
    }

    public XMLParser(String filePath) {
        this.filePath = filePath;
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
        } finally {
            bufferedReader.close();
        }

        return sbRawData;
    }

    public String[] formattingData(StringBuffer data) {
        String[] formatData = data.toString()
                .replaceAll(">\\s+", ">")
                .replace(">", ">\n")
                .replace("</", "\n</")
                .replaceAll("\n+", "\n")
                .split("\n");
        for (int i = 0; i < formatData.length; ++i) {
            formatData[i] = formatData[i].trim();
        }
        return formatData;
    }

    public Node createNodes(String[] formatData) {
        int counterOpenTag = 0;
        int counterCloseTag = 0;
        int counerLines = 0;
        LinkedList<Node> listOfNodes = new LinkedList<Node>();

        Pattern patternOpenTag = Pattern.compile(openTag);
        Pattern patternCloseTag = Pattern.compile(closeTag);

        for (int i = 0; i < formatData.length; ++i) {
            counerLines++;
            System.out.println(formatData[i]);
            if ("".equals(formatData[i])) {
                continue;
            }

            if (patternOpenTag.matcher(formatData[i]).matches() && node == null) {
                counterOpenTag++;

                node = new NodeBuilder(formatData[i]).build();
                listOfNodes.addLast(node);
                continue;
            } else if (listOfNodes.size() >= 1) {
                if (patternOpenTag.matcher(formatData[i]).matches()) {
                    counterOpenTag++;
                    Node current = new NodeBuilder(formatData[i]).build();
                    listOfNodes.getLast().addNode(current);
                    listOfNodes.addLast(current);
                    continue;
                } else if (patternCloseTag.matcher(formatData[i]).matches()) {
                    counterCloseTag++;
                    listOfNodes.removeLast();
                    continue;
                } else {
                    listOfNodes.getLast().setContent(formatData[i]);
                    continue;
                }
            }
        }
        System.out.println("open tags = " + counterOpenTag + "\t close tags = " + counterCloseTag + "\t lines = "
                + counerLines);

        return node;

    }

    public void printNodes(LinkedList<Node> nodes) {
        for (Node node : nodes) {
            System.out.println(node.toString());
        }
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }

    @Override
    public Node parse() throws IOException {
        StringBuffer stringBuffer = readXMLData(FILE_PATH);
        String formatData[] = formattingData(stringBuffer);
        Node root = createNodes(formatData);
        System.out.println("Good job");
        return root;
    }
}
