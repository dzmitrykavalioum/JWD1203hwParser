package com.dzmitrykavalioum.implementation;

import com.dzmitrykavalioum.entity.Node;

import java.io.IOException;

public interface Parser {

    Node parse( ) throws IOException;
}
