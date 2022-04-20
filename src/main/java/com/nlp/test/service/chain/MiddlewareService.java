package com.nlp.test.service.chain;

public abstract class MiddlewareService {

    private MiddlewareService next;

    public MiddlewareService linkWith(MiddlewareService next) {
        this.next = next;
        return next;
    }

    public abstract String analyse(String word);

    protected String analyseNext(String word) {
        if (next == null) {
            return word;
        }

        return next.analyse(word);
    }
}
