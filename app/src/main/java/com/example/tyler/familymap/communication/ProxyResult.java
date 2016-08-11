package com.example.tyler.familymap.communication;

/**
 * Created by Tyler on 7/27/2016.
 */
public class ProxyResult {

    private boolean hasError;
    private Exception error;
    private Object data;

    public ProxyResult(boolean hasError, Object data) {
        this.hasError = hasError;
        this.data = data;
    }

    public ProxyResult(boolean hasError, Exception error) {
        this(hasError, "");
        this.error = error;
    }

    public boolean hasError() {
        return hasError;
    }

    public Exception getError() {
        return error;
    }

    public Object getData() {
        return data;
    }
}
