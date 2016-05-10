package cz.zcu.kiv.oop.uml.test.generator.jenesis4java;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.jenesis4java.Access.AccessType;
import net.sourceforge.jenesis4java.Annotation;
import net.sourceforge.jenesis4java.Assign;
import net.sourceforge.jenesis4java.ClassField;
import net.sourceforge.jenesis4java.ClassLiteral;
import net.sourceforge.jenesis4java.ClassMethod;
import net.sourceforge.jenesis4java.Expression;
import net.sourceforge.jenesis4java.FieldAccess;
import net.sourceforge.jenesis4java.Invoke;
import net.sourceforge.jenesis4java.Let;
import net.sourceforge.jenesis4java.Namespace;
import net.sourceforge.jenesis4java.NewArray;
import net.sourceforge.jenesis4java.NewClass;
import net.sourceforge.jenesis4java.PackageClass;
import net.sourceforge.jenesis4java.Type;
import net.sourceforge.jenesis4java.Variable;
import net.sourceforge.jenesis4java.VirtualMachine;

public class Jenesis4JavaHelper {

  protected final VirtualMachine virtualMachine;

  protected Namespace namespace;
  protected PackageClass packageClass;

  protected final List<Class<?>> imports = new ArrayList<Class<?>>();

  public Jenesis4JavaHelper(VirtualMachine virtualMachine) {
    this.virtualMachine = virtualMachine;
  }

  public void set(Namespace namespace, PackageClass packageClass) {
    this.namespace = namespace;
    this.packageClass = packageClass;

    imports.clear();
  }

  public Class<?> getImportedClass(Class<?> clazz) {
    Package pckge = clazz.getPackage();
    if (pckge.getName().equals(namespace.getName()) || pckge.equals(Object.class.getPackage())) {
      return clazz;
    }

    if (!imports.contains(clazz)) {
      packageClass.addImport(clazz);
      imports.add(clazz);
    }

    return clazz;
  }

  public Annotation newAnnotation(Class<? extends java.lang.annotation.Annotation> annotation) {
    Class<?> importedClass = getImportedClass(annotation);

    return virtualMachine.newAnnotation(importedClass);
  }

  public NewArray newArray(Class<?> clazz) {
    Class<?> importedClass = getImportedClass(clazz);

    return virtualMachine.newArray(virtualMachine.newType(importedClass));
  }

  public FieldAccess newAccess(Class<?> clazz, Object fieldName) {
    Class<?> importedClass = getImportedClass(clazz);

    return virtualMachine.newAccess(importedClass, fieldName);
  }

  public ClassLiteral newClassLiteral(Class<?> clazz) {
    Class<?> importedClass = getImportedClass(clazz);

    return virtualMachine.newClassLiteral(virtualMachine.newType(importedClass));
  }

  public NewClass newClass(Class<?> clazz) {
    Class<?> importedClass = getImportedClass(clazz);

    return virtualMachine.newClass(virtualMachine.newType(importedClass));
  }

  public ClassField newField(Class<?> type, String name) {
    Class<?> importedClass = getImportedClass(type);
    ClassField field = packageClass.newField(importedClass, name);
    field.setAccess(AccessType.PUBLIC);

    return field;
  }

  public ClassField newField(Type type, String name) {
    ClassField field = packageClass.newField(type, name);
    field.setAccess(AccessType.PUBLIC);

    return field;
  }

  public ClassField newField(Class<?> type, String name, Expression expression) {
    ClassField field = newField(type, name);
    field.setAccess(AccessType.PUBLIC);
    field.setExpression(expression);

    return field;
  }

  public ClassMethod newMethod(Class<?> returnType, String name) {
    Class<?> importedClass = getImportedClass(returnType);

    return newMethod(virtualMachine.newType(importedClass), name);
  }

  public ClassMethod newMethod(int returnType, String name) {
    return newMethod(virtualMachine.newType(returnType), name);
  }

  public ClassMethod newMethod(Type returnType, String name) {
    ClassMethod method = packageClass.newMethod(returnType, name);
    method.setAccess(AccessType.PUBLIC);

    return method;
  }

  public Let newLocalVariable(ClassMethod method, Class<?> varType, String varName, Expression value) {
    Class<?> importedClass = getImportedClass(varType);

    return newLocalVariable(method, virtualMachine.newType(importedClass), varName, value);
  }

  public Let newLocalVariable(ClassMethod method, int varType, String varName, Expression value) {
    return newLocalVariable(method, virtualMachine.newType(varType), varName, value);
  }

  public Let newLocalVariable(ClassMethod method, Type varType, String varName, Expression value) {
    Let let = method.newLet(varType);
    let.addAssign(varName, value);

    return let;
  }

  public Invoke newInvoke(Class<?> invokationClass, String method) {
    Class<?> importedClass = getImportedClass(invokationClass);

    return virtualMachine.newInvoke(importedClass, method);
  }

  public Invoke newInvoke(Let let, String method) {
    return virtualMachine.newInvoke(getSimpleLocalVariableName(let), method);
  }

  public FieldAccess newFieldAccess(Class<?> clazz, String fieldName) {
    return virtualMachine.newAccess(getImportedClass(clazz), fieldName);
  }

  public Variable getSimpleLocalVariable(Let let) {
    Assign assign = let.getAssigns().get(0);

    return assign.getVariable();
  }

  public String getSimpleLocalVariableName(Let let) {
    Assign assign = let.getAssigns().get(0);

    return assign.getVariable().getName();
  }

  public Expression[] toExpressionArray(String[] stringArray) {
    Expression[] array = new Expression[stringArray.length];
    for (int i = 0; i < stringArray.length; i++) {
      if (stringArray[i] == null) {
        array[i] = virtualMachine.newNull();
      }
      else {
        array[i] = virtualMachine.newString(stringArray[i]);
      }
    }

    return array;
  }

  public <E extends Enum<?>> Expression[] toExpressionArray(Class<E> enumClass, E[] enumArray) {
    Expression[] array = new Expression[enumArray.length];
    for (int i = 0; i < enumArray.length; i++) {
      array[i] = virtualMachine.newAccess(enumClass, enumArray[i].name());
    }

    return array;
  }

  public NewArray newArray(String[] values) {
    NewArray array = newArray(String.class).addDim();
    array.setInitializer(virtualMachine.newArrayInit(toExpressionArray(values)));

    return array;
  }

  public <E extends Enum<?>> NewArray newArray(Class<E> enumClass, E[] values) {
    NewArray array = newArray(enumClass);
    array.addDim().setInitializer(virtualMachine.newArrayInit(toExpressionArray(enumClass, values)));

    return array;
  }

  public NewArray newArray(String[][] values) {
    NewArray array = newArray(String.class).addDim().addDim();
    Expression[] initializer = new Expression[values.length];
    for (int i = 0; i < values.length; i++) {
      NewArray array2 = newArray(String.class);
      array2.addDim().setInitializer(virtualMachine.newArrayInit(toExpressionArray(values[i])));
      initializer[i] = array2;
    }
    array.setInitializer(virtualMachine.newArrayInit(initializer));

    return array;
  }

}
