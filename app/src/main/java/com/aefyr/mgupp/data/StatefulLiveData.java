package com.aefyr.mgupp.data;

import androidx.lifecycle.LiveData;

/**
 * Created by Aefyr on 11.10.2018.
 */
public class StatefulLiveData<T> extends LiveData<T> {
    public enum State {
        UNINITIALIZED,
        LOADING,
        OK_LIVE,
        OK_CACHED,
        ERROR
    }

    private State mState = State.UNINITIALIZED;
    private Exception mError;
    private int mErrorCode;

    protected void setState(State state) {
        mState = state;
        setValue(getValue());
    }

    protected void setStateAndValue(State state, T value) {
        mState = state;
        setValue(value);
    }

    public State getState() {
        return mState;
    }

    public void setError(Exception error, int errorCode) {
        mError = error;
        mErrorCode = errorCode;
        setState(State.ERROR);
    }

    public Exception getError() {
        return mError;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}
