/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package de.ltux.mapstruct;

/**
 *
 * @author oliver.guenther
 */
public class Mapstruct {

    public static void main(String[] args) {
            Car c = new Car.Builder().description("hallo").build();
        CarDto cd = CarMapper.INSTANCE.carToCarDto(c);
        

        c = CarMapper.INSTANCE.carDtoToCar(cd);
        System.out.println(c);
    }
}
