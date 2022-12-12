package tasks;

import common.Person;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 {

  // Далее речь о строчке 'private long count', которая была удалена поскольку:
  // Какая прелесть! Поле класса с многозначительным философским названием.
  // Шож, посмотрим зачем она нужна на самом деле ...
  // Чуть позже: Так, понятно. Локальная по своей сути переменная, используемая только в методе countEven.
  // Удалим переменную как поле класса, приведя тем самым область её видимости в соответвии с назначением.

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    return persons.stream()
                  .skip(1) // Раз исплользуется Stream, то вместо удаления, просто пропускаем первый элемент
                  .map(Person::getFirstName)
                  .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  // Следовало бы разместить опеределение до getNames,
  // поскольку последний используется в getDifferentNames.
  // Тогда соблюдался бы принцип от общего (начало файла) к частному (вниз по файлу)
  // Так же, возможно, будь метод в классе с говорящим названием первоначальное название подошло бы,
  // а так уточним, что это getDifferentPersonsNames
  public Set<String> getDifferentPersonsNames(List<Person> persons) {
    // Set уже коллекция уникальных элементов => distinct был не нужен
    // В остальном это была длинная версия того, что делает конструктор HashSet
    return new HashSet<>(getNames(persons));
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  // Ах, этот философский вопрос именование методов.
  // Название запутает человека, решившего, что метод возвращает строковое предстваление Person.
  // На самом деле метод возвращает строку ФИО.
  // И да, String это деталь реализации, которую не 'зашивают' в имя.
  public String convertPersonToFullName(Person person) {
    // Конкатенация строк через оператор '+' в первоначальном варианте
    // приводила к генерации промежуточных строк на каждый вызов.
    // Ещё вместо MiddleName использовалось SecondName.
    // У меня Stream'изм, доктор?
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  //словарь id персоны -> ее имя
  // Как и в случаи с getDifferentPersonsNames и getPersonsNames, здесь тоже следовало бы разместить
  // convertPersonToFullName после определения getPairsIdAndFullNameForUniquePersonsIds.
  // Однако, это если следовать вышеизложенному принципу.
  public Map<Integer, String> getPersonsNames(Collection<Person> persons) {
    // Один раз поняв и подсев на Stream, потом сложно от него отказаться ;-(
    return persons.stream()
            .collect(toMap(Person::getId, this::convertPersonToFullName, (first, second) -> first));
  }

  //есть ли совпадающие в двух коллекциях персоны?
  // И снова и сразу об именовании. Раз сравнение двух коллекций, вызывается через третий метод, а не метод одной из них, то
  // можно сказать что ОНИ (they) имеют/нет одинаковые элементы, тогда по грамматике английского языка будет have вместо has.
  // Стандартная библиотека Java богата на различные функции. Посмотрим есть ли нечто похожее.
  // Хм, отлично есть disjoint который возвращает true "if the two specified collections have no elements in common".
  // Да, метод решает обратную задачу, но об этом позже.
  public boolean haveSamePersons(Collection<Person> first, Collection<Person> second) {
    // disjoint выбрасывает NullPointerException "if either collection is null"
    if (first == null || second == null)
      return false;
    // Берём отрицание от результата работы этой функции, поскольку как говорилось ранее нужно решить обратную задачу.
    return !Collections.disjoint(first, second);
  }

  //...
  // Изначально метод использовал в качестве типа формального параметра Stream, который ненадёжен в силу своей "одноразовости".
  public long countEven(Collection<Integer> nums) {
    // Переменная count по всем признакам локальная и её незачем делать полем класса.
    // Более того, она вообще не нужна поскольку... ага, в Stream есть метод count)
    return nums.stream().filter(num -> num % 2 == 0).count();
  }
}
