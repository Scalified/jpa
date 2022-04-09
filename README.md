# JPA Java Library

[![Build Status](https://github.com/Scalified/jpa/actions/workflows/gradle.yml/jpa.svg)](https://github.com/Scalified/jpa/actions)
[![Maven Central](https://img.shields.io/maven-central/v/com.scalified/jpa.svg)](https://search.maven.org/search?q=g:com.scalified%20AND%20a:jpa&core=gav)

## Description

This Library provides convenient DSL and extensions for working with [Java Persistence API](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html)

## Requirements

The Library requires JDK 11 or higher

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

// Finding entity by type and key
Person personJohn = jpa.find(Person.class).one("John");

// Finding entities by type
List<Person> personList = jpa.find(Person.class).list();

// Finding entities by type and mapping results to set
List<Person> personSet = jpa.find(Person.class).set();

// Finding entities by type and mapping results
List<Person> personList = jpa.find(Person.class).some(query -> query.setMaxResults(100).getResultList());

// Streaming entities by type
Stream<Person> personStream = jpa.find(Person.class).stream();

// Streaming entities by type specifying chunk size
Stream<Person> personStream = jpa.find(Person.class).stream(100);

// Finding first optional entity by type 
Optional<Person> personList = jpa.find(Person.class).first();


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

// Finding entities by criteria function and mapping results
Set<Person> personSet = jpa.find(builder -> {
    CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
    Root<Person> root = criteriaQuery.from(Person.class);
    return criteriaQuery.select(root);
}).some(query -> query.setMaxResults(100).getResultList());

// Streaming entities by criteria function
Stream<Person> personStream = jpa.find(builder -> {
    CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
    Root<Person> root = criteriaQuery.from(Person.class);
    return criteriaQuery.select(root);
}).stream();

// Streaming entities by criteria function specifying chunk size
Stream<Person> personStream = jpa.find(builder -> {
    CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
    Root<Person> root = criteriaQuery.from(Person.class);
    return criteriaQuery.select(root);
}).stream(100);

// Finding first optional entity by criteria function
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

}

Specification<Person> isFemaleSpecification = new IsFemaleSpecification();

// Combining multiple specifications into one AND condition specification
List<Person> youngFemalePersons = jpa.find(AndSpecification.of(isYoungSpecification, isFemaleSpecification)).list();

// Combining multiple specifications into one OR condition specification
List<Person> youngOrFemalePersons = jpa.find(OrSpecification.of(isYoungSpecification, isFemaleSpecification)).list();
```

> Streaming entities executes jpa queries for each chunk under the hood. In case if table is populated or modified 
during stream consuming, the new data will also be included into result set.

### Query DSL

**Query** DSL provides convenient way for queries execution

#### Raw SQL Queries Execution

```java
Jpa jpa;
// ... jpa initialization skipped

// Executing raw SQL query
int count = jpa.query("DELETE FROM PERSON WHERE ID = 1").execute();

// Raw SQL query
String sql = "SELECT * FROM PERSON"

// Executing raw SQL query and mapping results to set
Set<Person> resultSet = jpa.query(sql, Person.class).set();

// Executing raw SQL query and mapping results to list
List<Person> resultList = jpa.query(sql, Person.class).list();

// Executing raw SQL query and retrieving optional result
Optional<Person> optionalResult = jpa.query(sql, Person.class).first();
```

#### Stored Procedures Execution

```java
Jpa jpa;
// ... jpa initialization skipped

// Building stored procedure query configuration object
SpQuery<String> query = SpQuery.<String>builder("SOME_PROCEDURE")
				.withInParam("FIRST_PARAM", "FIRST_PARAM_VALUE")
				.withInParam("SECOND_PARAM", Arrays.asList("ONE", "TWO", "THREE"))
				.withRefCursorParam("THIRD_PARAM")
				.withParam("FOURTH_PARAM", ParameterMode.IN, "FOUR")
				.withResultClasses(SomeResultClass.class)
				.build();

// Calling stored procedure and mapping results to set
Set<String> resultSet = jpa.query(query).set();

// Calling stored procedure and mapping results to list
List<String> resultList = jpa.query(query).list();

// Calling stored procedure and retrieving optional result
Optional<String> optionalResult = jpa.query(query).first();
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

### AbstractRepository

**Jpa** provides an **AbstractRepository** abstract class, which provides common repositories methods:

```java
Jpa jpa;
// ... jpa initialization skipped

@Entity
class Person {
	
    @Id
    Integer id;

    String name;

    Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    void setName(String name) {
        this.name = name;
    }

}

// Repository
import com.scalified.jpa.repository.AbstractRepository

class PersonRepository extends AbstractRepository<Person> {
	
    PersonRepository(Jpa jpa) {
        super(jpa);
    }
	
}

PersonRepository repository = new PersonRepository(jpa);
Person person = new Person(1, "John");

// Adding an entity
repository.add(person);

// Replacing an entity
person.setName("Alice");
repository.replace(person);

// Removing an entity
repository.remove(person);
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
