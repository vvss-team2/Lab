package com.starfruit;

public class Set {

    int vS[];
    int nVS;

    Set(int capacity) {
        vS = new int[capacity];
        nVS = 0;
    }

    public boolean AddAValue(int newVal) {
        if (IsInTheSet(newVal)) {
            return false;
        }
        if (nVS >= vS.length) {
            throw new IndexOutOfBoundsException("Set capacity exceeded");
        }
        vS[nVS++] = newVal;
        return true;
    }

    public boolean IsInTheSet(int checkVal) {
        for (int i = 0; i < nVS; i++) {
            if (vS[i] == checkVal) {
                return true;
            }
        }
        return false;
    }

}
