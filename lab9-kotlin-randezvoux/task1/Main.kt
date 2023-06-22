package lab10.zad1

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select


suspend fun producer(channels: List<Channel<Int>>) {
    while (true) {
        val randNum = (0 until N).random()
        println("Try send: '$randNum'...")
        select<Unit> {
            for (i in 0 until N) {
                val smugglerId = (randNum + i) % N
                channels[smugglerId].onSend(smugglerId) {
                    println("P sent to: '$smugglerId'")
                }
            }
        }
    }
}

suspend fun smuggler(id: Int, channel: Channel<Int>, consumerChannel: Channel<Int>) {
    while (true) {
        select<Unit> {
            channel.onReceiveCatching {
                val value = it.getOrNull()
                if (value != null) {
                    println("C: '$id', 'received' -> '$value', sending...")
                    consumerChannel.send(value)
                }
            }
        }
    }
}

suspend fun consumer(channel: Channel<Int>) {
    while (true) {
        select<Unit> {
            channel.onReceiveCatching {
                val value = it.getOrNull()
                if (value != null) println("'received' -> '$value'") else println("Channel is closed")
            }
        }
    }
}

const val N = 5

fun main(): Unit = runBlocking {
    val channels = List(N) { Channel<Int>() }
    val consumerChannel = Channel<Int>()
    launch { consumer(consumerChannel) }
    for (smugglerId in 0 until N) {
        launch { smuggler(smugglerId, channels[smugglerId], consumerChannel) }
    }
    launch { producer(channels) }
}
