package br.com.tagliaferrodev.dextra.pottertest.integration

import br.com.tagliaferrodev.dextra.pottertest.house.House
import feign.Param
import feign.QueryMap
import feign.RequestLine

interface PotterExecutor {

    @RequestLine("GET {path}")
    fun getHouses(@Param("path") path: String,
                  @QueryMap query: HashMap<String, String>): List<House>

    @RequestLine("GET {path}/{id}")
    fun getHouseById(@Param("path") path: String,
                     @Param("id") id: String,
                     @QueryMap query: HashMap<String, String>): List<House>
}
