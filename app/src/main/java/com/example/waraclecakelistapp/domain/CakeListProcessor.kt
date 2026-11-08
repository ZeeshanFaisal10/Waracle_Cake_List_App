package com.example.waraclecakelistapp.domain

import com.example.waraclecakelistapp.domain.model.Cake

object CakeListProcessor {

    fun process(cakes: List<Cake>): List<Cake>{
        return cakes.distinctBy { it.title.trim().lowercase() }
            .sortedBy { it.title.trim().lowercase() }
    }
}

// TODO: Will consider locale-aware alphabetical sorting if the application
//       needs to support internationalized cake titles.