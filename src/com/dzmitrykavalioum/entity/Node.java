package com.dzmitrykavalioum.entity;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String tag;
    private List<Attribute> attributes = new ArrayList<Attribute>();
    private List<Node> nodes = new ArrayList<Node>();
    private String content;

    public Node() {
    }

    public Node(NodeBuilder nodeBuilder) {
        this.attributes = nodeBuilder.attributes;
        this.tag = nodeBuilder.tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNode(Node node) {
        this.nodes.add(node);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuffer nodeString = null;
        nodeString.append(getTag());
        for (Attribute data: attributes) {
            nodeString.append("\t"+data.getKey()+"="+data.getValue()+"\n");

        }
        nodeString.append("\t");
        for (Node node:nodes) {
            nodeString.append(node.toString()+"\n");
        }
        nodeString.append("\n");
        return nodeString.toString();
    }
}
