package com.itmuch.thrift.thrift;

import com.itmuch.thrift.Person;
import com.itmuch.thrift.PersonService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClient {


    public static void main(String args[]) throws Exception {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8200), 600);
        TProtocol protocol = new TCompactProtocol(transport);

        PersonService.Client client = new PersonService.Client(protocol);

        try{
            transport.open();
            Person person = client.getPersionByUsername("polunzi");
            System.out.println(person.toString());



            Person person1 = new Person();
            person1.setUsername("zhangsan");
            person1.setAge(22);
            person.setMarried(false);
            client.savePerson(person1);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            transport.close();
        }

    }
}
