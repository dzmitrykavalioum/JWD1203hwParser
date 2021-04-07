package com.dzmitrykavalioum.utils;

import com.dzmitrykavalioum.entity.Node;
import com.dzmitrykavalioum.entity.NodeBuilder;
import com.dzmitrykavalioum.exception.ParserException;
import com.dzmitrykavalioum.implementation.Parser;

import java.io.*;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class XMLParser implements Closeable, Parser {
    private final String openTag = "<[a-zA-Z:]+(\\s*[a-zA-Z:]*=\\\"[-a-zA-Z:\\s_0-9/\\.]*\\\")*>";
    private final String closeTag = "</[a-zA-Z:]+>";
    //private final String comment = "<!--(.*?)-->";

    private String filePath;
    BufferedReader bufferedReader = null;
    Node node;
    String COMMENT = "COMMENT";

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
                .replaceAll("\\t", " ")
                .replaceAll("<!--(.*?)-->","")
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
        int counterLines = 0;
        LinkedList<Node> listOfNodes = new LinkedList<Node>();
        Pattern patternOpenTag = Pattern.compile(openTag);
        Pattern patternCloseTag = Pattern.compile(closeTag);
//        Pattern patternComment = Pattern.compile(COMMENT);

        for (int i = 0; i < formatData.length; ++i) {
            counterLines++;
            if ("".equals(formatData[i])) {
                continue;
            }
            //TODO parsing comments in xml
//            if (patternComment.matcher(formatData[i]).matches()){
//                System.out.println(COMMENT);
//                continue;
//            }

            if (patternOpenTag.matcher(formatData[i]).matches() && node == null) {
                counterOpenTag++;
                node = new NodeBuilder(formatData[i]).build();
                listOfNodes.addLast(node);
            } else if (listOfNodes.size() >= 1) {
                if (patternOpenTag.matcher(formatData[i]).matches()) {
                    counterOpenTag++;
                    Node current = new NodeBuilder(formatData[i]).build();
                    listOfNodes.getLast().addNode(current);
                    listOfNodes.addLast(current);
                } else if (patternCloseTag.matcher(formatData[i]).matches()) {
                    counterCloseTag++;
                    listOfNodes.removeLast();
                } else {
                    listOfNodes.getLast().setContent(formatData[i]);
                }
            }
        }

        //TODO delete after debugging
        System.out.println("open tags = " + counterOpenTag + "\t close tags = " + counterCloseTag + "\t lines = "
                + counterLines);

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
        StringBuffer stringBuffer = readXMLData(filePath);
        String formatData[] = formattingData(stringBuffer);
        Node root = createNodes(formatData);
        return root;
    }
}

