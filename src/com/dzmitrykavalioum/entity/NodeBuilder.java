package com.dzmitrykavalioum.entity;

import java.util.ArrayList;
import java.util.List;

public class NodeBuilder {
    public String tag;
    public List<Attribute> attributes = new ArrayList<Attribute>();

    public NodeBuilder(String node) {
        String[] description = node.substring(1, node.length() - 1).split("\"| |=", -1);
        this.tag = description[0];
        if (description.length != 1) {
            for (int i = 1; i < description.length - 1; i += 2) {
                System.out.println(description[i]);
                attributes.add(new Attribute(description[i], description[i + 1]));
            }
        }

    }

    public Node build() {
        return new Node(this);
    }
}
