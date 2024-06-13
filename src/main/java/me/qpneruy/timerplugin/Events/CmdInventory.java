package me.qpneruy.timerplugin.Events;

import me.qpneruy.timerplugin.Task.ExecutionCmd;
import org.bukkit.inventory.Inventory;
import me.qpneruy.timerplugin.Task.archiver;
public class CmdInventory {
    private final Inventory inventory;
    private final ExecutionCmd SchedCmd;
    private final archiver archiver;

    public CmdInventory (Inventory inventory, ExecutionCmd cmd, archiver archiver) {
        this.inventory = inventory;
        this.SchedCmd = cmd;
        this.archiver = archiver;
    }
    public Inventory getInventory() {
        return this.inventory;
    }
    public ExecutionCmd getCmd(){
        return this.SchedCmd;
    }
    public archiver getArchiver() { return this.archiver;}
}
