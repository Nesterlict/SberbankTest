package SberbankTest.controller;

import SberbankTest.entity.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Split {


    /**
     * Main algorithm to calculate values for Persons
     * This method works with person's wallets and make them equal for biggest wallet first.
     * If there are still money in the bank after this procedure it will equally split it among them.
     * If we don't have enough money to make it equal for all this algorithm will add money to those who have less
     * splitting it among lowest wallets
     * In this case we achieve most equal situation for all.
     * @param personList
     * @param wallet
     */
    public static void mainSplit(List<Person> personList, BigDecimal wallet) {

        Map<Integer, BigDecimal> map = personList.stream().collect(Collectors.toMap(Person::getIndex, Person::getWallet));
        Map<Integer,BigDecimal> sortedmap = sortByValue(map);
        ArrayList<BigDecimal> values = new ArrayList<>(sortedmap.values());

        for (int i = 1; i < values.size(); i++) {
            BigDecimal dif = values.get(i).subtract(values.get(i - 1));
            if (dif.multiply(BigDecimal.valueOf(i)).compareTo(wallet) == -1) {
                for (int j = 0; j < i; j++) {
                    values.set(j, values.get(j).add(dif));
                    wallet = wallet.subtract(dif);
                }
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

        List<Integer> keySet = new ArrayList<>(sortedmap.keySet());
        Map<Integer,BigDecimal> updatedmap = new LinkedHashMap<>();
        for (int i=0;i<keySet.size();i++){
            updatedmap.put(keySet.get(i),values.get(i));
        }

        for (int i=0;i<personList.size();i++){
            personList.get(i).setWallet(updatedmap.get(i));
            personList.get(i).setAppendFromBank(updatedmap.get(i).subtract(sortedmap.get(i)));
        }

    }

    /**
     * Method for splitting wallet equally if we already make person's wallets equal
     * or if we don't have enough to do it
     * @param x BigDecimal for splitting
     * @param n amount of parts to split
     * @return
     */
    public static ArrayList<BigDecimal> equalSplit(BigDecimal x, int n) {
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

    /**
     * Method to sort map by value
     * @param map
     * @param <K>
     * @param <V>
     * @return sorted LinkedHashMap
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    /**
     * Method for get list of persons with lowest append value
     * @param personList list of Persons
     * @param n amount of persons
     * @return list with names of persons with lowest append value
     */
    public static ArrayList<String>  minAppend(List<Person> personList, int n){
        Map<Integer, BigDecimal> map = personList.stream().collect(Collectors.toMap(Person::getIndex, Person::getAppendFromBank));
        Map<Integer,BigDecimal> sortedmap = sortByValue(map);
        List<Integer> keySet = new ArrayList<>(sortedmap.keySet());
        ArrayList<String> result = new ArrayList<>();
        for(int i =0;i<n;i++){
            result.add(i,personList.get(keySet.get(i)).getName());
        }
        return result;
    }
}


