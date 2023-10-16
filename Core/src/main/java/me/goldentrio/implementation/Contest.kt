package me.goldentrio.implementation

import java.util.*
import me.goldentrio.IO as IOInterface

internal class Contest(private val configuration: Configuration) {

    fun call(solve: IOInterface.() -> Unit) {
        configuration.input.forEach { (input, write) ->
            val inputQueue: Deque<Deque<String>> = input
                .replace("\r", "")
                .split("\n")
                .map { it.split(" ").toCollection(LinkedList()) }
                .toCollection(LinkedList())
            val outputQueue = LinkedList<Deque<String>>()

            val io = IO(inputQueue, outputQueue)
            io.repeat = {
                if (inputQueue.any { it.isNotEmpty() }) io.solve()
            }

            io.solve()

//            val executor = Executors.newSingleThreadExecutor()
//            val future = executor.submit { io.solve() }
//
//            try {
//                future.get(5, TimeUnit.SECONDS)
//            } catch (e: ExecutionException) {
//                throw e.cause ?: e
//            } finally {
//                executor.shutdownNow()
//            }

            write(outputQueue.joinToString("\n") { it.joinToString(" ") })
        }
    }

}