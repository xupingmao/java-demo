package com.xpm.jdk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xupingmao on 2017/10/13.
 */
public class MyRegExp {

    private int position;
    private String pattern;

    public static class State {
        public Set<Character> characters = new HashSet<>();
        public boolean star = false;
        public boolean plus = false;
        public List<State> next = new ArrayList<>();
    }

    private char read() {
        if (position < pattern.length()) {
            char c = pattern.charAt(position);
            position += 1;
            return c;
        } else {
            return 0;
        }
    }

    public State compile(String regex) {
        pattern = regex;
        State root = new State();
        State last = root;
        char c = read();
        while (c != 0) {
            switch (c) {
                case '*': {
                    last.star = true;
                    break;
                }
                case '+': {
                    last.plus = true;
                    break;
                }
                default:{
                    State state = new State();
                    state.characters.add(c);

                    last.next.add(state);
                    last = state;
                    break;
                }
            }
        }
        return root;
    }

}
