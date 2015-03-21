package gunParser;

enum Config {

    instance(false, true);

    private boolean useSeveralThreads;
    private boolean shouldMakeDelays;
    private String proxy = null;

    Config(boolean useSeveralThreads, boolean shouldMakeDelays) {
        this.useSeveralThreads = useSeveralThreads;
        this.shouldMakeDelays = shouldMakeDelays;
    }

    public Config useSeveralThreads(boolean useSeveralThreads) {
        this.useSeveralThreads = useSeveralThreads;
        return this;
    }

    public Config shouldMakeDelays(boolean shouldMakeDelays) {
        this.shouldMakeDelays = shouldMakeDelays;
        return this;
    }

    public Config proxy(String proxy) {
        this.proxy = proxy;
        return this;
    }

    public boolean isUseSeveralThreads() {
        return useSeveralThreads;
    }

    public boolean isShouldMakeDelays() {
        return shouldMakeDelays;
    }

    public String getProxy() {
        return proxy;
    }
}
