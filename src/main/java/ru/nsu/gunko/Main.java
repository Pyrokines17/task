package ru.nsu.gunko;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

public class Main {
    public static void main(String[] args) {
        List<Pair<BufferedReader, Boolean>> readers;

        Parser parser = new Parser();
        Sorter sorter = new Sorter();

        readers = parser.configure(args, sorter);
        sorter.sort(readers);
    }
}