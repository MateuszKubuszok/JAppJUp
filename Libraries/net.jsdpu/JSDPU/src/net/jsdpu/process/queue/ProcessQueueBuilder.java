package net.jsdpu.process.queue;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.jsdpu.process.elevated.ElevatedProcessBuilder;

// TODO: convert to actual class
public class ProcessQueueBuilder {
	private ArrayList<ProcessBuilder> processBuilders;
	private Map<ElevatedProcessBuilder, ProcessBuilder> elevatedProcessBuilders; 
	
	/**
	 * Creates builder that allows running several processes as one.
	 */
	public ProcessQueueBuilder() {
		processBuilders = new ArrayList<ProcessBuilder>();
		elevatedProcessBuilders = new HashMap<ElevatedProcessBuilder, ProcessBuilder>();
	}
	
	/**
	 * Enqueues ProcessBuilders for queue.
	 * 
	 * @param processBuilders builders to be enqueued
	 * @return this builder allowing chaining
	 */
    public ProcessQueueBuilder enqueue(ProcessBuilder... processBuilders) {
    	this.processBuilders.addAll(asList(processBuilders));
    	return this;
    }

    /**
	 * Enqueues ElevatedProcessBuilders for queue.
	 * 
	 * @param elevatedProcessBuilders builders to be enqueued
	 * @return this builder allowing chaining
	 */
    public ProcessQueueBuilder enqueue(ElevatedProcessBuilder... elevatedProcessBuilders) {
    	for (ElevatedProcessBuilder elevatedProcessBuilder : elevatedProcessBuilders) {
    		ProcessBuilder processBuilder = elevatedProcessBuilder.getProcessBuilder();
    		this.elevatedProcessBuilders.put(elevatedProcessBuilder, processBuilder);
    		processBuilders.add(processBuilder);
    	}
    	return this;
    }

    /**
	 * Dequeues ProcessBuilders for queue.
	 * 
	 * @param processBuilders builders to be dequeued
	 * @return this builder allowing chaining
	 */
    public ProcessQueueBuilder dequeue(ProcessBuilder... processBuilders) {
    	this.processBuilders.removeAll(asList(processBuilders));
    	return this;
    }
    
    /**
	 * Dequeues ElevatedProcessBuilders for queue.
	 * 
	 * @param elevatedProcessBuilders builders to be dequeued
	 * @return this builder allowing chaining
	 */
    public ProcessQueueBuilder dequeue(ElevatedProcessBuilder... elevatedProcessBuilders) {
    	for (ElevatedProcessBuilder elevatedProcessBuilder : elevatedProcessBuilders)
    		if (this.elevatedProcessBuilders.containsKey(elevatedProcessBuilder)) {
    			ProcessBuilder processBuilder = this.elevatedProcessBuilders.get(elevatedProcessBuilder);
    			this.processBuilders.remove(processBuilder);
    			this.elevatedProcessBuilders.remove(elevatedProcessBuilder);
    		}	
    	return this;
    }

    public void build() {
    	
    }
}
