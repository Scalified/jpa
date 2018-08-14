# JPA Java Library

[![Build Status](https://travis-ci.org/Scalified/jpa.svg)](https://travis-ci.org/Scalified/jpa)
[![Maven Central](https://img.shields.io/maven-central/v/com.scalified/jpa.svg)](https://search.maven.org/search?q=g:com.scalified%20AND%20a:jpa&core=gav)

## Description

This Library provides convenient DSL and extensions for working with [Java Persistence API](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html)

## Requirements

The Library requires [Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher

## Gradle dependency

```java
dependencies {
	compile "com.scalified:jpa:$VERSION"
}
```

## Changelog

[Changelog](CHANGELOG.md)

## Usage

DSL and extensions are accessible via **Jpa** object

### Instantiation

**Jpa** can be instantiated providing an instance of **javax.persistence.EntityManager**:

```java
EntityManager em;
// ... em initialization skipped

Jpa jpa = new JpaImpl(em);
```

**Jpa** internally uses **JpaManager**, a wrapper around **javax.persistence.EntityManager**, which performs all operations against the real **javax.persistence.EntityManager**.
The default implementation of the wrapper, which is used by **Jpa** when instantiated using an instance of **javax.persistence.EntityManager** is **JpaStandardManager**

**JpaStandardManager** internally does not provide any specific options: all write operations are flushed at the end and no transactions are created.
**JpaStandardManager** can be decorated with the following implementations:

* **JpaTransactionalManager** - performs all write operations in a new transaction
* **JpaSynchronizedManager** - performs all write operations in a java synchronized way

```java
EntityManager em;
// ... em initialization skipped

Jpa jpa = new JpaImpl(new JpaTransactionalManager(new JpaStandardManager(em))); // decorate with JpaTransactionManager only
Jpa jpa = new JpaImpl(new JpaSynchronizedManager(new JpaTransactionalManager(new JpaStandardManager(em)))); // decorate with both
```

### Find DSL

**Find** DSL provides convenient way of selecting entities

```java
Jpa jpa;
// ... jpa initialization skipped

// Example entity class
@Entity
class Person {

    @Id
    String name;

    int age;

    Gender gender;

    enum Gender {
        MALE,
        FEMALE
    }

}

// Finding entity by key
Person personJohn = jpa.find(Person.class).one("John");

// Finding entities by criteria function and mapping results to list
List<Person> personList = jpa.find(builder -> {
    CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
    Root<Person> root = criteriaQuery.from(Person.class);
    return criteriaQuery.select(root);
}).list();

// Finding entities by criteria function and mapping results to set
Set<Person> personSet = jpa.find(builder -> {
    CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
    Root<Person> root = criteriaQuery.from(Person.class);
    return criteriaQuery.select(root);
}).set();

// Finding entities by criteria function and mapping results to stream
Stream<Person> personStream = jpa.find(builder -> {
    CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
    Root<Person> root = criteriaQuery.from(Person.class);
    return criteriaQuery.select(root);
}).stream();

// Finding optional entity
Optional<Person> person = jpa.find(builder -> {
    CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
    Root<Person> root = criteriaQuery.from(Person.class);
    return criteriaQuery.select(root);
}).first();


// Finding with Specification Pattern
class IsYoungSpecification implements Specification<Person> {

    private static final int MAX_YOUNG_AGE_YEARS = 20;

    @Override
    public boolean isSatisfiedBy(Person what) {
        return what.getAge() < MAX_YOUNG_AGE_YEARS;
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<Person> root) {
        return builder.lessThan(root.get(Person._age), MAX_YOUNG_AGE_YEARS);
    }

    @Override
    public Class<Person> getType() {
        return Person.class;
    }

}

Person person = new Person(Gender.FEMALE, 20);
Specification<Person> isYoungSpecification = new IsYoungSpecification();
boolean isYoung = isYoungSpecification.isSatisfiedBy(person); // checks whether person satisfies specification

List<Person> youngPersons = jpa.find(isYoungSpecification).list(); // finds list of young persons

class IsFemaleSpecification implements Specification<Person> {

    @Override
    public boolean isSatisfiedBy(Person what) {
        return what.getGender() == Gender.FEMALE;
    }

    @Override
    public Predicate toPredicate(CriteriaBuilder builder, Root<Person> root) {
        return builder.equal(root.get(Person._gender), Gender.FEMALE);
    }

    @Override
    public Class<Person> getType() {
        return Person.class;
    }

}

Specification<Person> isFemaleSpecification = new IsFemaleSpecification();

// Combining multiple specifications into one AND condition specification
List<Person> youngFemalePersons = jpa.find(new AndSpecification<>(isYoungSpecification, isFemaleSpecification)).list();

// Combining multiple specifications into one OR condition specification
List<Person> youngOrFemalePersons = jpa.find(new OrSpecification<>(isYoungSpecification, isFemaleSpecification)).list();
```

### From DSL

**From** DSL provides useful methods based on entity class

```java
Jpa jpa;
// ... jpa initialization skipped

// Example entity class
@Entity
class Person {

    @Id
    String name;

}

// Counting all persons
long allPersonsCount = jpa.from(Person.class).count();

// Counting all persons, whose name is John
long personsWithNameJohnCount = jpa.from(Person.class).count((builder, root) -> builder.equal(root.get(Person_.name), "John"));
```

### Entity DSL

**Entity** DSL provides write and management operations on a single entity object

```java
Jpa jpa;
// ... jpa initialization skipped

@Entity
class Person {

    @Id
    String name;

    Person(String name) {
        this.name = name;
    }

    void setName(String name) {
        this.name = name;
    }

}

// Inserting a person
Person person = new Person("John");
Person inserted = jpa.entity(person).insert();

// Updating a person
person.setName("Alex");
Person updated = jpa.entity(person).update();

// Deleting a person
jpa.entity(person).delete();

// Refreshing person's state
jpa.entity(person).refresh();

// Detaching a person from entity context
jpa.entity(person).detach();
```

### Entities DSL

**Entities** DSL provides write and management operations on a collection of entity objects

```java
Jpa jpa;
// ... jpa initialization skipped

@Entity
class Person {

    @Id
    String name;

    Person(String name) {
        this.name = name;
    }

    void setName(String name) {
        this.name = name;
    }

}

// Inserting colleciton of persons
Set<Person> persons = Stream.of(new Person("John"), new Person("Alex")).collect(Collectors.toSet());
Set<Person> inserted = jpa.entities(persons).insert();

// Updating collection of persons
persons.forEach(person -> person.setName("Bob"));
Set<Person> updated = jpa.entities(persons).update();

// Deleting collection of persons
jpa.entities(persons).delete();

// Refreshing each person's state in a collection
jpa.entities(persons).refresh();

// Detaching each person in collection from entity context
jpa.entities(persons).detach();
```

## License

```
MIT License

Copyright (c) 2018 Scalified

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Scalified Links

* [Scalified](http://www.scalified.com)
* [Scalified Official Facebook Page](https://www.facebook.com/scalified)
* <a href="mailto:info@scalified.com?subject=[JPA Java Library]: Proposals And Suggestions">Scalified Support</a>
