package com.marche.audiobookier.features.detail

import com.marche.audiobookier.data.model.Pokemon
import com.marche.audiobookier.data.model.Statistic
import com.marche.audiobookier.features.base.MvpView

interface DetailMvpView : MvpView {

    fun showPokemon(pokemon: Pokemon)

    fun showStat(statistic: Statistic)

    fun showProgress(show: Boolean)

    fun showError(error: Throwable)

}