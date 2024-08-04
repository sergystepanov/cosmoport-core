package com.cosmoport.core.node;

public final class NodesHolder {
    private int tables = 0;
    private int gates = 0;

    public synchronized int getTables() {
        return tables;
    }

    public synchronized int getGates() {
        return gates;
    }

    public synchronized void incTables() {
        this.tables++;
    }

    public synchronized void incGates() {
        this.gates++;
    }

    public synchronized void decTables() {
        this.tables--;
    }

    public synchronized void decGates() {
        this.gates--;
    }
}
