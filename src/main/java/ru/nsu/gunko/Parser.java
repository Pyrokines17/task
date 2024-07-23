package ru.nsu.gunko;

import java.io.*;
import java.util.*;
import java.nio.file.*;

import org.apache.commons.lang3.tuple.Pair;

public class Parser {
    private final List<Pair<BufferedReader, Boolean>> readers;

    public Parser() {
        this.readers = new ArrayList<>();
    }

    public List<Pair<BufferedReader, Boolean>> configure(String[] args, Sorter sorter) {
        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-o": {
                    i += 1;

                    if (i == args.length) {
                        System.err.println("Error in setting path: no path provided");
                        break;
                    }

                    Path path = Paths.get(args[i]);

                    if (Files.exists(path)) {
                        sorter.setPath(args[i]);
                    } else {
                        i -= 1;
                        System.err.print("Error in setting path: "+args[i+1]);
                        System.err.println(" -- path does not exist (maybe it's a file)");
                    }

                    break;
                }
                case "-p": {
                    i += 1;

                    if (i == args.length) {
                        System.err.println("Error in setting prefix: no prefix provided");
                        break;
                    }

                    sorter.setPrefix(args[i]);
                    break;
                }
                case "-a": {
                    sorter.setAdd(true);
                    break;
                }
                case "-s": {
                    sorter.setStat(1);
                    break;
                }
                case "-f": {
                    sorter.setStat(2);
                    break;
                }
                default: {
                    initReader(args[i]);
                    break;
                }
            }
        }

        return readers;
    }

    private void initReader(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            boolean isDone = false;
            readers.add(Pair.of(reader, isDone));
        } catch (IOException e) {
            System.err.println("Error in initialization of reader: "+e.getMessage());
        }
    }
}
