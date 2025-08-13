package github.qbic.darkflame.events;

public abstract class SingleUseEvent extends ModEvent {
    private boolean used = false;

    @Override
    public void execute() {
        used = true;
    }

    @Override
    public boolean canUse() {
        return !used;
    }
}
