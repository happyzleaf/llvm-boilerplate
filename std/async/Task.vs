package "async"

/**
 * Represents a helper class for creating specifid timer tasks. 
 * It features repeating and delaying the execution of specific runnable tasks.
 */
abstract class Task {
public:   
    /**
     * Delay the execution of the target task with the specified timeout.
     * 
     * @param task the target task to be executed delayed
     * @param delay the unix delay timeout
     * @return a new started delayed task
     */
    static Task delay(|| task, long delay) {
        return new DelayedTask(task)
    }

    /**
     * Repetitively execute the target task, the specified number of `times`.
     * 
     * @param task the target task to be executed repetitively
     * @param times the number of times the task should be executed in total
     * @return a new started repeating task
     */
    static Task repeat(|| task, int times = 0) {
        return new RepeatingTask(task)
    }

    /**
     * Repetitively execute the target task, and wait the specified timeout in between each task execution.
     * 
     * @param task the target task to be executed repetitively
     * @param first the timeout waited before the first execution
     * @param delay the timeout waited in between each task execution
     * @return a new started repeating task
     */
    static Task repeat(|| task, long first, long delay) {
        return new RepeatingTask(task)
    }

    /**
     * Repetitively execute the target task, and wait the specified timeout in between each task execution.
     * 
     * @param task the target task to be executed repetitively
     * @param delay the timeout waited befure each task execution
     * @return a new started repeating task
     */
    static Task repeat(|| task, long delay) {
        return new RepeatingTask(task)
    }

    /**
     * Execute the specified task asynchronously. The task may be explicitly async or sync
     */
    static Task run(|| task) {
        panic("Not implemented yet")
    }
}

/**
 * Represents a task that is continuously being called repetitively using the specifeid parameters..
 * `times` indicates, how many times the task should be executed. If it is `0` or below, the task
 * will run infinitely. `first` is the time interval that is waited before the `first` execution of
 * the task. `delay` is the time interwal that is waited in between each task execution.
 */
@Builder
class RepeatingTask {
public:
    /**
     * The maximum number of times, that the task should be executed.
     */
    int times

    /**
     * The time interwal that is awaited before the first execution of the task.
     */
    long first

    /**
     * The time interval that is awaited in between every execution of the task.
     */
    long delay

    /**
     * Indicate, that the repeating task had been started.
     */
    @Exclude(Builder)
    bool running

public:
    /**
     * Initialize the repeating task.
     * @task the target task to be repeated
     */
    RepeatingTask(private || task) {
    }

    /**
     * Start the repating task. Wait `first` milliseconds, before the first execution,
     * and then wait `delay` in between every following tasks.
     */
    @Override
    void start() = synchronized (this) {
        let updates = 0
        let start   = Time.now()
        let last    = 0
        running     = true
        // update the task whilst the timer is active 
        while (running) {
            // wait for the timer to exceed the first timeout
            let now = Time.now()
            if (updates == 0) {
                if (now - start < first)
                    continue
            }
            // wait for the timer to exceed the timeout between each execution
            else if (now - last < delay)
                continue
            // execute the task and terminate the timer if the maximum number of updates had been done
            task()
            last = now
            if (++updates == times)
                running = false
        }
    }

    /**
     * Cancel the repeating timer task. 
     */
    @Override
    void cancel() = synchronized (this) {
        running = false
    }
}

/**
 * Represents a task that is executed after the specified `timeout` had been exceeded.
 */
@Builder(Exclusive)
class DelayedTask {
public:
    /**
     * The time that is waited before the execution of the task.
     */
    @Include(Builder)
    long timeout
    
    /**
     * The start time of the task execution.
     */
    long start

    /**
     * Indicate, whether the task had been cancelled.
     */
    bool cancelled = false

    /**
     * Initialize the delayed task.
     * @task the target task to be executed after the timeout
     */
    DelayedTask(private || task) {
    }

    /**
     * Start the delayed task. Execute the specified task after the specified amount of time
     * had been exceeded.
     */
    @Override
    void start() = synchronized (this) {
        start = Time.now()
        // wait for timer to exceed the timeout
        loop {
            if (Time.now() - start < timeout)
                continue
        }
        // execute the task if the timer hadn't been cancelled whilst it was waiting
        if (!cancelled)
            task()
    }

    /**
     * Cancel the delayed timer task.
     */
    @Override
    void cancel() = synchronized (this) {
        cancelled = true
    }
}
