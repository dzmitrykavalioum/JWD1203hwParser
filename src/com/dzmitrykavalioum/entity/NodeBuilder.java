package com.dzmitrykavalioum.entity;

import java.util.ArrayList;
import java.util.List;

public class NodeBuilder {
    public String tag;
    public List<Attribute> attributes = new ArrayList<Attribute>();

    public NodeBuilder(String childNode) {
        //String[] description = value.substring(1, value.length() - 1).split("\"| |=", -1);
        String[] description = childNode.substring(1, childNode.length() - 1).split(" ");

        this.tag = description[0];
        if (description.length != 1) {
            for (int i = 1; i < description.length - 1; i += 1) {
                String attribute[] = description[i].split("=");
                String key = attribute[0];
                String value = attribute[1].replace("\"","");
                System.out.println(description[i]);
                attributes.add(new Attribute(key, value));
            }
        }

    }

    public Node build() {
        return new Node(this);
    }
}
