package HVL.Scheduler;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class PSJFScheduler implements Scheduler {

    private Queue<Task> ready;
    private Task selected;

    PSJFScheduler() {
        this.ready = new ArrayDeque<>();
	this.selected = null;
    }

    @Override
    public Optional<Integer> scheduled() {
        if(selected == null) return Optional.empty();
        return Optional.of(selected.getId());
    }

    @Override
    public List<Integer> ready() {
        return ready.stream().map(Task::getId).toList();
    }

    // Subtask 2(a): Complete the implementation of Preemptive Shortest Job First    
    @Override
    public void addTask(Task task) {
        ready.add(task);
        if (selected != null && task.getRemaining() < selected.getRemaining()) {
            selected.stop();
            ready.add(selected);
            selected = null;
        }
    }

    @Override
    public void schedule() {
        if(selected == null) {
            selected = ready.stream()
                    .min((t1, t2) -> Integer.compare(t1.getRemaining(), t2.getRemaining()))
                    .orElse(null);
            if(selected == null){
                return;
            }
            ready.remove(selected);
            selected.start();
        } else {
            if(selected.isDone()){
                selected.stop();
                selected = null;
                schedule();
            } else {

                Task shortest = ready.stream()
                        .min((t1, t2) -> Integer.compare(t1.getRemaining(), t2.getRemaining()))
                        .orElse(null);
                if(shortest != null && shortest.getRemaining() < selected.getRemaining()){
                    selected.stop();
                    ready.add(selected);
                    selected = shortest;
                    ready.remove(shortest);
                    selected.start();
                }
            
            }
        }
    }

}
