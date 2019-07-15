package com.itmuch.thrift;

import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersionByUsername(String username) throws TException {
        Person person = new Person();
        person.setUsername("polunzi");
        person.setAge(24);
        person.setMarried(false);

        return person;
    }

    @Override
    public void savePerson(Person Person) throws TException {
        System.out.println(Person.toString());
    }
}
