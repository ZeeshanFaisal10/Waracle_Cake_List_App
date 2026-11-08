package com.example.waraclecakelistapp

import com.example.waraclecakelistapp.domain.CakeListProcessor
import com.example.waraclecakelistapp.domain.model.Cake
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CakeListProcessorTest {

    @Test
    fun `process removes duplicate cakes ignoring case and whitespace`() {
        val cakesList = listOf(
            Cake("Chocolate Cake", "Chocolate sponge", "image1"),
            Cake(" chocolate cake ", "Another description", "image2"),
            Cake("Apple Cake", "Apple sponge", "image3")
        )

        val result = CakeListProcessor.process(cakesList)

        assertEquals(2, result.size)
        assertEquals(
            listOf("Apple Cake","Chocolate Cake"),
            result.map { it.title }
        )
    }

    @Test
    fun `sorting the cakes alphabetically ignoring case`() {

        val cakesList = listOf(
            Cake("Zebra Cake", "Description", "image1"),
            Cake("banana Cake", "Description", "image2"),
            Cake("Apple Cake", "Description", "image3"),
            Cake("chocolate Cake", "Description", "image4")
        )

        val result = CakeListProcessor.process(cakesList)
        assertEquals(listOf(
            "Apple Cake",
            "banana Cake",
            "chocolate Cake",
            "Zebra Cake"
        ), result.map { it.title })
    }

    @Test
    fun `remove duplicates and sorts cakes alphabetically (combined unit test)`() {
        val cakes = listOf(
            Cake("Zebra Cake", "Zebra description", "image1"),
            Cake("banana Cake", "Banana description", "image2"),
            Cake("Apple Cake", "Apple description", "image3"),
            Cake("Banana Cake", "Duplicate banana", "image4"),
            Cake("chocolate Cake", "Chocolate description", "image5"),
            Cake(" apple cake ", "Duplicate apple", "image6")
        )

        val result = CakeListProcessor.process(cakes)

        assertEquals(
            listOf(
                "Apple Cake",
                "banana Cake",
                "chocolate Cake",
                "Zebra Cake"
            ),
            result.map { it.title }
        )

        assertEquals(
            "Banana description",
            result[1].description
        )
    }

    @Test
    fun `return empty list when input is empty`() {

        val cakes = emptyList<Cake>()

        val result = CakeListProcessor.process(cakes)

        assertTrue(result.isEmpty())
    }
}