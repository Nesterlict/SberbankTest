package SberbankTest.entity;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name="Person")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Person {

    private int index;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private BigDecimal wallet;

    @XmlAttribute
    private BigDecimal appendFromBank;

}
