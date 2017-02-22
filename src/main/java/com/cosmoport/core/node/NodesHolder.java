package com.cosmoport.core.node;

public final class NodesHolder {
    private int tables = 0;
    private int gates = 0;

    public int getTables() {
        return tables;
    }

    public int getGates() {
        return gates;
    }

    public void incTables() {
        this.tables++;
    }

    public void incGates() {
        this.gates++;
    }

    public void decTables() {
        this.tables--;
    }

    public void decGates() {
        this.gates--;
    }
}
