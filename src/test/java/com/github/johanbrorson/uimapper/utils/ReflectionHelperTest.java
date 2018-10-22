package com.github.johanbrorson.uimapper.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReflectionHelperTest {

  @Test
  public void testGetDeclaredFields() {
    final Iterable<Field> fields = ReflectionHelper.getDeclaredFields(TestClass.class);

    List<String> names = StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(fields.iterator(), Spliterator.ORDERED),
        false)
        .map(field -> field.getName())
        .collect(Collectors.toList());

    Assert.assertTrue(names.contains("a"));
    Assert.assertTrue(names.contains("b"));
    Assert.assertTrue(names.contains("c"));
  }

  @Test
  public void testSetField() throws Exception {
    TestClass object = new TestClass();
    Field field = object.getClass().getDeclaredField("c");
    boolean wasAccessible = field.isAccessible();
    ReflectionHelper.setField(object, field, "newValue");
    Assert.assertEquals(field.isAccessible(), wasAccessible);
    field.setAccessible(true);
    Assert.assertEquals(field.get(object), "newValue");
  }

  @Test
  public void testSetFieldRestoresAccessibleFlag() throws Exception {
    TestClass object = new TestClass();
    Field field = object.getClass().getDeclaredField("c");
    field.setAccessible(false);
    ReflectionHelper.setField(object, field, "newValue");
    Assert.assertEquals(field.isAccessible(), false);
  }

  class TestClass {
    public String a = "a";
    protected String b = "b";
    private String c = "c";

    @Override
    public String toString() {
      return String.format("%s[a=%s, b=%s, c=%s]",
          getClass().getSimpleName(), a, b, c);
    }
  }
}
