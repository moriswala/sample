package com.company.mohammedyakub.di.component;

import android.app.Application;

import com.company.mohammedyakub.MyApp;
import com.company.mohammedyakub.di.builder.ActivityBuilder;
import com.company.mohammedyakub.di.module.ApiServiceModule;
import com.company.mohammedyakub.di.module.ApplicationModule;
import com.company.mohammedyakub.di.module.OkHttpClientModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {ApplicationModule.class ,  AndroidInjectionModule.class , ActivityBuilder.class})
public interface ApplicationComponent {

    void inject(MyApp daggerExampleApp);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Builder AppModule(ApplicationModule module);

        Builder ApiService(ApiServiceModule module);

        Builder httpModule(OkHttpClientModule module);

        ApplicationComponent build();
    }
}
