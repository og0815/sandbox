/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module de.ltux.main.two {
    requires pojo.one; // automatic module name generation. NB11.1 still doesn't handle that
    requires de.ltux.pojo.two; // direct module usage
}
