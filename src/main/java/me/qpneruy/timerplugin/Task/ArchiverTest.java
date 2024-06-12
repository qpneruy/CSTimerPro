package me.qpneruy.timerplugin.Task;

import java.util.Map;
import java.util.logging.Logger;

public class ArchiverTest {
    private static final Logger logger = Logger.getLogger(ArchiverTest.class.getName());

    public static void main(String[] args) {
        // Initialize archiver
        archiver archiverInstance = new archiver();

        // Test adding a command
        System.out.println("Adding command...");
        byte addResult = archiverInstance.AddCommand("TestCommand1", "echo Hello World", "Thu_Hai", "08:00");
        System.out.println("Add result: " + addResult);

        System.out.println("Adding command...");
        byte add1Result = archiverInstance.AddCommand("Te1", "echo Hello World", "Thu_Hai", "08:00");
        Map<String, ExecutionCmd> alo = archiverInstance.getDateTime("Thu_Hai");
        for (Map.Entry<String, ExecutionCmd> entry : alo.entrySet() ) {
            ExecutionCmd a1 = entry.getValue();
            a1.ToggleEachMinutes();
        }
        System.out.println("Add result: " + add1Result);

        // Test adding a command with a specific date
        System.out.println("Adding command with date...");
        byte addDateResult = archiverInstance.AddCommand("TestCommand2", "echo Hello Date", "06/22/2024", "09:00");
        System.out.println("Add date result: " + addDateResult);


        // Test removing a command
        System.out.println("Removing command...");
        boolean removeResult = archiverInstance.RemoveCommand("Thu_Hai", "TestCommand1");
        System.out.println("Remove result: " + removeResult);
        System.out.println("Removing command...");
        boolean removeResult1 = archiverInstance.RemoveCommand("06/22/2024", "TestCommand2");
        System.out.println("Remove result: " + removeResult1);


    }
}
