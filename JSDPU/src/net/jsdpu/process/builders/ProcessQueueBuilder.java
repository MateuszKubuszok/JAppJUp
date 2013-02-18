package net.jsdpu.process.builders;

// TODO: convert to actual class
public interface ProcessQueueBuilder {
    public ProcessQueueBuilder enqueue(ProcessBuilder... processBuilder);

    public ProcessQueueBuilder enqueue(ElevatedProcessBuilder... processBuilder);

    public ProcessQueueBuilder dequeue(ProcessBuilder... processBuilder);

    public ProcessQueueBuilder dequeue(ElevatedProcessBuilder... processBuilder);

    public void build();
}
