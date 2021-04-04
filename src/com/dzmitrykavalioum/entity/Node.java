package com.dzmitrykavalioum.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Serializable {
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

    public void addNode(Node node) {
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
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(tag, node.tag) && Objects.equals(attributes, node.attributes) && Objects.equals(nodes, node.nodes) && Objects.equals(content, node.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, attributes, nodes, content);
    }
}
