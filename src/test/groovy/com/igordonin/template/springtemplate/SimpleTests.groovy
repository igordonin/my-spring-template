package com.igordonin.template.springtemplate

import spock.lang.Specification
import spock.lang.Unroll

class SimpleTests extends Specification {
    def "computing the maximum of two numbers"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }

    @Unroll
    def "The largest number between #firstNumber and #secondNumber is #expectedMax"() {
        expect:
        Math.max(firstNumber, secondNumber) == expectedMax

        where:
        firstNumber | secondNumber || expectedMax
        5           | 1            || 5
        3           | 9            || 9
    }
}