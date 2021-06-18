package SberbankTest.controller;

import SberbankTest.entity.Bank;
import java.io.File;

import static SberbankTest.controller.Split.mainSplit;
import static SberbankTest.controller.XMLhandler.buildXML;
import static SberbankTest.controller.XMLhandler.readXML;

public class Main {
    public static void main(String[] args) {
        File file = new File("src/main/resources/Bank.xml");
        Bank bank = readXML(file);
        for(int i=0;i<bank.getPersonList().size();i++){
            bank.getPersonList().get(i).setIndex(i);
        }
        mainSplit(bank.getPersonList(), bank.getWallet());
        buildXML(bank.getPersonList(),Split.minAppend(bank.getPersonList(),3));

    }
}
