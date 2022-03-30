package ru.pinkgoosik.visuality.util;

import java.util.Random;

public enum NoteColors {
    // Octave 1
    Fb1(0, "F#", 7853824),
    G1(11, "G", 9814016),
    Gb1(2, "G#", 11707648),
    A1(13, "A", 13403648),
    Ab1(4, "A#", 14836992),
    B1(15, "B", 15941888),
    C1(16, "C", 16522752),
    Cb1(7, "C#", 16646159),
    D1(18, "D", 16187443),
    Db1(9, "D#", 15204442),
    E1(10, "E", 13566083),
    F1(11, "F", 11403433),

    // F#
    Fb(12, "F#", 8782028),

    // Octave 2
    G2(13, "G", 5964007),
    Gb2(14, "G#", 2949369),
    A2(15, "A", 133886),
    Ab2(16, "A#", 14326),
    B2(17, "B", 26848),
    C2(18, "C", 39612),
    Cb2(19, "C#", 50829),
    D2(20, "D", 59736),
    Db2(21, "D#", 64545),
    E2(22, "E", 64545),
    F2(23, "F", 5892096),
    Fb2(24, "F#", 9748736);

    private final int index;
    private final String note;
    private final int color;

    NoteColors(int index, String note, int color) {
        this.index = index;
        this.note = note;
        this.color = color;
    }

    public static int getRandomColor() {
        Random random = new Random();
        int size = NoteColors.values().length;

        return NoteColors.values()[random.nextInt(size)].getColor();
    }

    public int getColor() { return this.color; }
}
