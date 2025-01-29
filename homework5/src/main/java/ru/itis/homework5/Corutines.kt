package ru.itis.homework5

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    var x = 5 //число корутин, которое будет запущено
    runBlocking {
        val jobsList = mutableListOf<Deferred<Unit>>()

        //это если мы создаем пкорутины параллельно
/*        repeat (x) { count ->
            jobsList.add(async(start = CoroutineStart.DEFAULT) {
                delay(1000L)
                println("Корутина запущена $count")
            })
        }*/

        //это если мы создаем пкорутины последовательно
        jobsList.add(async(start = CoroutineStart.DEFAULT) {
            repeat(x) {count ->
                delay(1000L)
                println("Корутина запущена $count")
            }
        })

        //отменить корутины при сворачивании приложения
        var job: Job? = null


    }
}