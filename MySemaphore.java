public class MySemaphore {
    private volatile int semaphore; // 1 is free

    public MySemaphore() {
        this.semaphore = 1; // free by default
    }

    public void lock() {
        semaphore = 0;
    }

    public void free() {
        semaphore = 1;
    }

    public boolean isLocked() {
        return semaphore == 0;
    }

    public boolean isFree() {
        return semaphore == 1;
    }
}
