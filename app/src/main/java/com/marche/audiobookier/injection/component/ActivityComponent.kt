package com.marche.audiobookier.injection.component

import com.marche.audiobookier.features.base.BaseActivity
import com.marche.audiobookier.features.main.MainActivity
import com.marche.audiobookier.injection.PerActivity
import com.marche.audiobookier.injection.module.ActivityModule
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)
}
