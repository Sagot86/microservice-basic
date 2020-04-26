package org.example.basic.util;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Clock for test purposes.
 *
 * @author Igor_Orlov
 */
public class TestClock extends Clock {

    private final Clock initialClock;
    private final AtomicReference<Clock> delegateRef;

    public TestClock() {
        this(Clock.systemUTC());
    }

    public TestClock(Clock delegate) {
        this.initialClock = delegate;
        this.delegateRef = new AtomicReference<>(delegate);
    }

    public void reset() {
        setClock(initialClock);
    }

    @Override
    public ZoneId getZone() {
        return delegateRef.get().getZone();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new TestClock(delegateRef.get().withZone(zone));
    }

    @Override
    public Instant instant() {
        return delegateRef.get().instant();
    }

    public void setFixedInstant(Instant instant) {
        delegateRef.updateAndGet(delegate -> Clock.fixed(instant, delegate.getZone()));
    }

    public void setClock(Clock clock) {
        delegateRef.set(clock);
    }
}
