package lab10.zad2

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*


fun CoroutineScope.producer(sendChannel: SendChannel<Int>) = produce<Int> {
    while (true) {
        println("P: (0) sending... -> 0")
        sendChannel.send(0)
        println("P: (0) SENT -> 0")
    }
}

fun CoroutineScope.smuggler(id: Int, receiveChannel: ReceiveChannel<Int>, sendChannel: SendChannel<Int>) = produce<Int> {
    while (true) {
        for (num in receiveChannel) {
            println("S: ($id) received -> '$num', sending... -> '$id'")
            sendChannel.send(id)
            println("S: ($id) SENT -> '$id'")
        }
    }
}

fun CoroutineScope.consumer(receiveChannel: ReceiveChannel<Int>) = launch {
    val id = N + 1
    while (true) {
        for (num in receiveChannel) {
            println("C: ($id) received -> '$num'")
        }
    }
}

const val N = 5

fun main(): Unit = runBlocking {
    val producerChannel = Channel<Int>()
    val smugglerChannels = MutableList(N) { Channel<Int>() }
    val consumerChannel = Channel<Int>()
    smugglerChannels.add(0, producerChannel)
    smugglerChannels.add(smugglerChannels.lastIndex, consumerChannel)

    consumer(consumerChannel)
    for (smugglerId in 0 until N) {
        smuggler(smugglerId + 1, smugglerChannels[smugglerId], smugglerChannels[smugglerId + 1])
    }
    producer(producerChannel)
}
