# The Singleton Design Pattern

This document describes why and how we implemented the singleton design pattern in our project.


## Why Singletons?

We implemented the singleton design pattern because we noticed the large amount of classes that only contained static methods. Nearly all of them are located in the handlers package. This is logical, since handlers are supposed to carry out one specific task, without the need of an instance of the class. 

We noticed that the code did not really look clean with all those classnames in front of methods. Instead, we could declare an instance variable in each class using the static methods to access those. A requirement would be that only one instance is allowed to exist, as it would make no sense to have multiple instances when the class does not maintain any state. How did we do that?

## How have they been implemented?

First of all, we started refactoring the classes that were going to follow the singleton design pattern. A static instance variable of the singleton class was created, initializing the unique instance of the class:

    private static Singleton instance = new Singleton();

Since this instance is static, it already exists before a the class instance can exist. Therefore, this approach is thread safe.

A private constructor was added to override the default constructor:

    private Singleton() {}

This constructor cannot be accessed from outside the singleton class because it has the private modifier, so no other class can even try to create a new instance of the singleton.

Then, a public static method was added for other classes to retrieve the singleton instance:

    public static Singleton getInstance() {
        return instance;  
    }

Since this method is static, the singleton is accessed without having to create a new instance of the class. In classes that use the instance, we could just add an instance variable to retrieve it once and use it throughout the code. 

For this to actually work, the methods that used to be static were refactored to public methods in order to be able to access them through the instance of the singleton.
