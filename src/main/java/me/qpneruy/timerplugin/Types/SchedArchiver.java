package me.qpneruy.timerplugin.Types;

import me.qpneruy.timerplugin.Task.archiver;

public class SchedArchiver {
    private final ExecutionCmd child;
    private final archiver WhoisParentofthiChild;

    public SchedArchiver(ExecutionCmd child, archiver archiver) {
        this.child = child;
        this.WhoisParentofthiChild = archiver;
    }

    public ExecutionCmd getChild() {
        return this.child;
    }

    public archiver getParent() {
        return this.WhoisParentofthiChild;
    }
}
