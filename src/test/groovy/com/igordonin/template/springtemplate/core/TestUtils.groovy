package com.igordonin.template.springtemplate.core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.MediaType
import spock.lang.Specification

import java.math.MathContext
import java.nio.charset.Charset

import static java.lang.Integer.parseInt
import static java.lang.Long.parseLong
import static java.lang.Math.random
import static java.math.RoundingMode.HALF_EVEN
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

class TestUtils extends Specification {

    static String asJsonString(final Object obj) {
        try {
            def objectMapper = new ObjectMapper()
            objectMapper.registerModule(new JavaTimeModule())
            return objectMapper.writeValueAsString(obj)
        } catch (Exception e) {
            throw new RuntimeException(e)
        }
    }

    static generateGuid() {
        randomAlphanumeric(36)
    }

    static generateString(length = 10) {
        def charset = (('A'..'Z') + ('0'..'9')).join()
        RandomStringUtils.random(length, charset.toCharArray())
    }

    static generateId() {
        parseLong(randomNumeric(10))
    }

    static Double generateDouble() {
        random() * generateInteger()
    }

    static BigDecimal generateDecimalsOnly() {
        def value = random()
        BigDecimal.valueOf(value).setScale(4, HALF_EVEN)
    }

    static BigDecimal generateBigDecimal(precision = 5, scale = 2) {
        def randomDouble = random() * generateLong(precision)

        def mathContext = new MathContext(precision + scale, HALF_EVEN)
        def bigDecimal = new BigDecimal(randomDouble, mathContext)

        bigDecimal.round(scale)
    }

    static generateLong(precision = 10) {
        parseLong(randomNumeric(precision))
    }

    static generateInteger(precision = 8) {
        parseInt(randomNumeric(precision))
    }

    static generateBoolean() {
        generateInteger() % 2 == 0
    }

    static replaceDependency(component, dependencyName, newDependency) {
        def hasProp = component.hasProperty(dependencyName)

        if (hasProp) {
            component[dependencyName] = newDependency
            return
        }

        def target = component.targetSource.target
        replaceDependency(target, dependencyName, newDependency)
    }

    static randomElement(collection) {
        collection.shuffled().first()
    }

    static final MediaType CONTENT_TYPE_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    )
}
