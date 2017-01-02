package com.pacoworks.rxsealedunions.reducer;

import com.pacoworks.rxsealedunions.Union;

import rx.functions.Func1;

public class CombinedReducers<U extends Union, S extends State> implements Func1<U, S> {

    public static <U, S> CombinedReducers<Union, State> from(Func1<?, ?>... reducers) {
        return new CombinedReducers<>(reducers);
    }

    private final Func1<U, S>[] reducers;

    @SuppressWarnings("unchecked") private CombinedReducers(Func1<?, ?>[] reducers) {
        this.reducers = (Func1<U, S>[]) reducers;
    }

    @Override public S call(U union) {
        S state = null;
        for (Func1<U, S> reducer : reducers) {
            state = reducer.call(union);
        }
        return state;
    }
}
