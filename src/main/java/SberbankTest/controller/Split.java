package SberbankTest.controller;

import SberbankTest.entity.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Split {



    public List<Person> mainSplit(List<Person> personList, BigDecimal wallet) {
        //ArrayList<BigDecimal> values = personList.stream().map(Person::getWallet).collect(Collectors.toCollection(ArrayList::new));
        //System.out.println(Arrays.toString(values.toArray()));
        //Collections.sort(values);
        //System.out.println(Arrays.toString(values.toArray()));

        Map<Integer, BigDecimal> map = personList.stream().collect(Collectors.toMap(Person::getIndex, Person::getWallet));
        System.out.println(map.keySet().stream().map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}")));

        Map<Integer,BigDecimal> newmap = sortByValue(map);
        System.out.println(newmap.keySet().stream().map(key -> key + "=" + newmap.get(key))
                .collect(Collectors.joining(", ", "{", "}")));


        ArrayList<BigDecimal> values = new ArrayList<>(newmap.values());
        System.out.println(Arrays.toString(values.toArray()));

        for (int i = 1; i < values.size(); i++) {
            BigDecimal dif = values.get(i).subtract(values.get(i - 1));
            if (dif.multiply(BigDecimal.valueOf(i)).compareTo(wallet) == -1) {
                for (int j = 0; j < i; j++) {
                    values.set(j, values.get(j).add(dif));
                    wallet = wallet.subtract(dif);
                }
                System.out.println("dif" + dif);
            } else {
                ArrayList<BigDecimal> list = equalSplit(wallet, i);
                for (int j = 0; j < i; j++) {
                    values.set(j, values.get(j).add(list.get(j)));
                }
                wallet = BigDecimal.valueOf(0);
                break;
            }
        }
        if (wallet.compareTo(BigDecimal.ZERO) != 0) {
            ArrayList<BigDecimal> list = equalSplit(wallet, values.size());
            for (int j = 0; j < values.size(); j++) {
                values.set(j, values.get(j).add(list.get(j)));
            }
        }
        System.out.println(Arrays.toString(values.toArray()));

        List<Integer> keySet = new ArrayList<>(newmap.keySet());
        System.out.println(keySet);
        Map<Integer,BigDecimal> newnewmap = new LinkedHashMap<>();
        for (int i=0;i<keySet.size();i++){
            newnewmap.put(keySet.get(i),values.get(i));
        }
        System.out.println(newnewmap);

        for (int i=0;i<personList.size();i++){
            personList.get(i).setWallet(newnewmap.get(i));
        }
        System.out.println(personList);
        return null;
    }


    public ArrayList<BigDecimal> equalSplit(BigDecimal x, int n) {
        ArrayList<BigDecimal> list = new ArrayList<>();
        BigDecimal res[];
        BigDecimal hundred = new BigDecimal(100);
        res = x.multiply(hundred).divideAndRemainder(BigDecimal.valueOf(n));
        list.add(0, res[0].add(res[1]).divide(hundred).setScale(2, RoundingMode.HALF_UP));
        for (int i = 1; i < n; i++) {
            list.add(i, res[0].divide(hundred).setScale(2, RoundingMode.HALF_UP));
        }
        return list;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}


