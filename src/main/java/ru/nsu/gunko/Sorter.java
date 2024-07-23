package ru.nsu.gunko;

import java.io.*;
import java.util.*;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.math.NumberUtils;

public class Sorter {
    private final Vector<BufferedWriter> writers;
    private final Vector<Integer> counts;
    // strings, integers, floats

    private String prefix;
    private String path;

    private boolean add;
    private int stat;

    private final Statist statist;

    public Sorter() {
        writers = new Vector<>(3);
        writers.addAll(Collections.nCopies(3, null));

        counts = new Vector<>(3);
        counts.addAll(Collections.nCopies(3, 0));

        this.prefix = "";
        this.path = ".";

        this.add = false;
        this.stat = 0;

        statist = new Statist();
    }

    public void sort(List<Pair<BufferedReader, Boolean>> readers) {
        int done = 0;
        String line;

        while (done < readers.size()) {
            for (int i = 0; i < readers.size(); ++i) {
                if (readers.get(i).getRight()) {
                    continue;
                }

                try {
                    line = readers.get(i).getLeft().readLine();

                    if (line == null) {
                        readers.get(i).getLeft().close();
                        readers.set(i, Pair.of(null, true));
                        done++;
                    } else {
                        writeLine(line);
                    }
                } catch (IOException e) {
                    System.err.println("Error in sorting: "+e.getMessage());
                }
            }
        }

        finish();
    }

    private void writeLine(String line) throws IOException {
        if (!NumberUtils.isCreatable(line)) {
            if (writers.getFirst() == null) {
                File file = new File(path, prefix + "strings.txt");
                writers.set(0, new BufferedWriter(new FileWriter(file, add)));
            }

            writers.getFirst().write(line);
            writers.getFirst().newLine();
            counts.set(0, counts.getFirst() + 1);

            if (stat == 2) {
                statist.strAnalyse(line);
            }
        } else {
            if (line.contains(".")) {
                if (writers.get(2) == null) {
                    File file = new File(path, prefix + "floats.txt");
                    writers.set(2, new BufferedWriter(new FileWriter(file, add)));
                }

                writers.get(2).write(line);
                writers.get(2).newLine();
                counts.set(2, counts.get(2) + 1);

                if (stat == 2) {
                    statist.floAnalyse(Double.parseDouble(line));
                }
            } else {
                if (writers.get(1) == null) {
                    File file = new File(path, prefix + "integers.txt");
                    writers.set(1, new BufferedWriter(new FileWriter(file, add)));
                }

                writers.get(1).write(line);
                writers.get(1).newLine();
                counts.set(1, counts.get(1) + 1);

                if (stat == 2) {
                    statist.intAnalyse(Float.parseFloat(line));
                }
            }
        }
    }

    private void finish() {
        switch (stat) {
            case 2: {
                statist.printStat();
            }
            case 1: {
                printCounts();
            }
            default: {
                break;
            }
        }

        for (BufferedWriter writer : writers) {
            safeClose(writer);
        }
    }

    private void printCounts() {
        System.out.println("Counts of lines:");

        for (int i = 0; i < counts.size(); ++i) {
            System.out.println((i == 0 ? "String" : i == 1 ? "Integer" : "Float")+": "+counts.get(i));
        }
    }

    private void safeClose(BufferedWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error in closing of writer: "+e.getMessage());
        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }
}
