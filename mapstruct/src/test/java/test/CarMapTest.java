/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import de.ltux.mapstruct.Car;
import de.ltux.mapstruct.CarDto;
import de.ltux.mapstruct.CarMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author oliver.guenther
 */
public class CarMapTest {

    @Test
    public void mapCar() {
        Car c = new Car.Builder().description("hallo").build();
        CarDto cd = CarMapper.INSTANCE.carToCarDto(c);
        Assertions.assertThat(c.description()).isEqualTo(cd.description());

        // Failed, weil Optionals nicht zu constants automatisch gemapped werden und ich finde das auch nicht.
        c = CarMapper.INSTANCE.carDtoToCar(cd);
        Assertions.assertThat(cd.description()).isEqualTo(c.description());

    }

}
