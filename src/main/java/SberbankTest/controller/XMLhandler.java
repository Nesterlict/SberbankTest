package SberbankTest.controller;

import SberbankTest.entity.Bank;
import SberbankTest.entity.Person;
import com.jamesmurty.utils.XMLBuilder2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class XMLhandler {

    public static Bank readXML(File file) {
        JAXBContext jaxbContext;
        Bank bank = new Bank();
        try {
            jaxbContext = JAXBContext.newInstance(Bank.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            bank = (Bank) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return bank;
    }

    public static void buildXML(List<Person> personList) {
        XMLBuilder2 builder = XMLBuilder2.create("total").e("result");
        for (int i = 0; i < personList.size(); i++) {
            builder.e("Person")
                    .a("name", personList.get(i).getName())
                    .a("wallet", personList.get(i).getWallet().toString())
                    .a("appendFromBank", personList.get(i).getWallet().toString());
        }
        builder.up();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src/main/resources/Result.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        builder.toWriter(writer, null);
    }
}
