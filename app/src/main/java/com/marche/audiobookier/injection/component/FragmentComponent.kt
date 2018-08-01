package com.marche.audiobookier.injection.component

import com.marche.audiobookier.injection.PerFragment
import com.marche.audiobookier.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent