test-utils
==========

# POJOAssert

That object useful for testing big POJOs. For example entities with lot of DB fields. You always need to not forget all fields in tests and write for all fields something like:

```
#!java

assertEquals(pojo.getProperty1(), "some value");
```

but with this you can do:

```
#!java

POJOAssert pa = new POJOAssert();
pa.ignore("field1", "field2");
pa.expect("property1", "some value");
pa.assert(pojo);
```

or even like this:


```
#!java

new POJOAssert()
    .ignore("field1", "field2")
    .expect("property1", "some value")
    .assert(pojo);
```

If pojo will have more porperties then *field1*, *field2* or *property1* in pojo, your test will not pass.
