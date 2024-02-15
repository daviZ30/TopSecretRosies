package com.moronlu18.item

import com.google.common.truth.Truth
import com.moronlu18.item.entity.ItemDao
import com.moronlu18.item.entity.ItemId
import com.moronlu18.item.entity.item
import com.moronlu18.item.entity.itemType
import org.junit.Test

class ItemTest {

    //Configuracion
   private val itemTest : item = item(ItemId(1),"itemtest",23.46,itemType.PRODUCT,"para pruebas", false,0.02)


    @Test
    fun `cambiar a metodo servicie`(){
        val type : itemType = itemType.SERVICE
       // val stateString = itemType.toString()
        //assert
        itemTest.type = type

    }
}