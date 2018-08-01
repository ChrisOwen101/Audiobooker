package com.marche.audiobookier.common.injection.component

import dagger.Component
import com.marche.audiobookier.common.injection.module.ApplicationTestModule
import com.marche.audiobookier.injection.component.AppComponent
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationTestModule::class))
interface TestComponent : AppComponent