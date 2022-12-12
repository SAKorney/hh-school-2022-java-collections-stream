package tasks;

import common.Person;

import java.util.*;
import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Не, ну, а шо? Практика после лекции по unit-тестированию
public class Task8Test {
    private static Task8 task;
    private static Instant now;

    @BeforeAll
    static void before() {
        task = new Task8();
        now = Instant.now();
    }

    @Test
    void getPersonsNamesEmptyList() {
        List<Person> persons = Collections.emptyList();
        assertEquals(task.getNames(persons), Collections.emptyList());
    }

    @Test
    void getPersonsNamesOneElement() {
        List<Person> persons = Stream.of(
                new Person(-1, "Dummy", now)).toList();
        var expectedResult = persons.stream().skip(1).map(Person::getFirstName).toList();
        assertEquals(task.getNames(persons), expectedResult);
    }

    @Test
    void getPersonsNamesSuccess() {
        List<Person> persons = Stream.of(
                new Person(-1, "Dummy", now),
                new Person(1, "Tester", now)).toList();
        var expectedResult = persons.stream().skip(1).map(Person::getFirstName).toList();
        assertEquals(task.getNames(persons), expectedResult);
    }

    @Test
    void getDifferentPersonsNamesSuccess() {
        List<Person> persons = Stream.of(
                new Person(-1, "Dummy", now),
                new Person(1, "Tester", now),
                new Person(2, "Tester", now),
                new Person(3, "User", now)).toList();
        assertEquals(task.getDifferentPersonsNames(persons), Set.of("Tester", "User"));
    }

    @Test
    void convertPersonToFullNameOnlyFirstName() {
        Person person = new Person(1, "Tester", now);
        assertEquals(task.convertPersonToFullName(person), person.getFirstName());
    }

    @Test
    void convertPersonToFullNameFirstAndSecond() {
        Person person = new Person(1, "Tester", "SecondName", now);
        var expectedResult = new StringBuilder()
                .append(person.getSecondName())
                .append(" ")
                .append(person.getFirstName());
        assertEquals(task.convertPersonToFullName(person), expectedResult.toString());
    }

    @Test
    void convertPersonToFullNameSuccess() {
        Person person = new Person(1, "Tester", "SecondName", "MiddleName", now);
        var expectedResult = new StringBuilder()
                .append(person.getSecondName())
                .append(" ")
                .append(person.getFirstName())
                .append(" ")
                .append(person.getMiddleName());
        assertEquals(task.convertPersonToFullName(person), expectedResult.toString());
    }

    @Test
    void getPersonsShortInfoTest()
    {
        List<Person> persons = Stream.of(
                new Person(1, "Tester", now),
                new Person(1, "User", now)).toList();

        assertEquals(task.getPersonsNames(persons), Map.of(1, "Tester"));
    }

    @Test
    void haveSamePersonsFalse()
    {
        List<Person> first = Stream.of(
                new Person(1, "Tester", now),
                new Person(2, "User", now)).toList();

        List<Person> second = Stream.of(
                new Person(3, "Tester", now),
                new Person(4, "User", now)).toList();

        assertFalse(task.haveSamePersons(first, second), "Прямое сравнение не выполняется");
        assertFalse(task.haveSamePersons(second, first), "Коммутативность не выполняется");
    }

    @Test
    void haveSamePersonsTrue()
    {
        List<Person> first = Stream.of(
                new Person(1, "Tester", now),
                new Person(2, "User", now)).toList();

        List<Person> second = Stream.of(
                new Person(1, "Tester", now),
                new Person(2, "User", now)).toList();

        assertTrue(task.haveSamePersons(first, second), "Прямое сравнение не выполняется");
        assertTrue(task.haveSamePersons(second, first), "Коммутативность не выполняется");
    }
}
