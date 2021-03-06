package SberbankTest.entity;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Bank entity for parsing from xml file with JAXB
 */
@XmlRootElement(name = "Bank")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Bank {

    @XmlAttribute
    private BigDecimal wallet;

    @XmlElement(name = "Person", type = Person.class)
    private List<Person> personList = null;


}
