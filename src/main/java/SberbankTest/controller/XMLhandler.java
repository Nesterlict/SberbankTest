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
import java.util.ArrayList;
import java.util.List;

/**
 * Class for working with XML files
 */
public class XMLhandler {

    /**
     * Method for reading data from xml file
     * @param file Bank xml file
     * @return Bank object
     */
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

    /**
     * Method for building xml file.
     *
     * Note: for some reason I have issue with putting minimum append person list in "minimum" element in xml file.
     * It works only with direct method chaining (builder.e("minimum").e("Person).a(...)...)
     * and when I use loop to add it into builder, list added to "result" element.
     * Method rewritten to show output similar to example.
     * It still satisfies both requirements from the task but looks a little bit different
     * minimum element used to split Person list and minimum append person list
     *
     * @param personList list of persons from bank with their data
     * @param min number of persons with minimum append value to write into xml file
     */
    public static void buildXML(List<Person> personList, ArrayList<String> min) {
        XMLBuilder2 builder = XMLBuilder2.create("total")
                .e("result");
        for (Person person : personList)
            builder.e("Person")
                    .a("name", person.getName())
                    .a("wallet", person.getWallet().toString())
                    .a("appendFromBank", person.getAppendFromBank().toString()).up();
        builder.e("minimum");
        for (String name : min) {
            builder.e("Person")
                    .a("name", name);
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src/main/resources/Result.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        builder.toWriter(writer, null);
    }
}
