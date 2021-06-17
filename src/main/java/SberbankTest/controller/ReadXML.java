package SberbankTest.controller;

import SberbankTest.entity.Bank;
import SberbankTest.entity.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static SberbankTest.controller.XMLhandler.*;

public class ReadXML {

    public static void main(String[] args) {

        File file = new File("src/main/resources/Bank.xml");
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Bank.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Bank bank = (Bank) jaxbUnmarshaller.unmarshal(file);
            System.out.println(bank);
            System.out.println(bank.getPersonList().get(0).getName());

            buildXML(bank.getPersonList());

        } catch (JAXBException e) {
            e.printStackTrace();
        }


    }
}
