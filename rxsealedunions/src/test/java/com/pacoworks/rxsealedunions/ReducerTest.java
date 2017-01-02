package com.pacoworks.rxsealedunions;

import com.pacoworks.rxsealedunions.reducer.CombinedReducers;
import com.pacoworks.rxsealedunions.reducer.State;

import org.junit.Test;

import rx.functions.Func1;

import static org.junit.Assert.assertNotNull;

public class ReducerTest {

    interface MyAction<A extends String, S extends MyState> {
        A getPayload();
        S getState();
    }

    interface Init extends MyAction {}

    interface Error extends MyAction {}

    interface Next extends MyAction {}

    interface None extends MyAction {}

    interface Login extends MyAction {}

    interface Logout extends MyAction {}

    private class MyState implements State {

        private String state;

        MyState(String state) {
            this.state = state;
        }

        @Override public String toString() {
            return state;
        }
    }

    private Func1<Union4<Init, Error, Next, None>, MyState> commonReducer = (union) -> union.join(
            init -> {
                System.out.println(init.getPayload());
                return new MyState("init");
            },
            error -> {
                System.out.println(error.getPayload());
                return new MyState("error");
            },
            next -> {
                System.out.println(next.getPayload());
                return new MyState(next.getPayload());
            },
            None::getState
    );

    private Func1<Union3<Login, Logout, None>, MyState> userReducer = (union) -> union.join(
            login -> {
                System.out.println(login.getPayload());
                return new MyState("User logged");
            },
            logout -> {
                System.out.println(logout.getPayload());
                return new MyState("User log out");
            },
            None::getState
    );

    @Test public void testStore() {
        assertNotNull(CombinedReducers.from(commonReducer, userReducer));
    }
}
