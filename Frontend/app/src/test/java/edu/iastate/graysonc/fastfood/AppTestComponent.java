package edu.iastate.graysonc.fastfood;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import edu.iastate.graysonc.fastfood.di.component.AppComponent;
import edu.iastate.graysonc.fastfood.di.module.ActivityModule;
import edu.iastate.graysonc.fastfood.di.module.AppModule;
import edu.iastate.graysonc.fastfood.di.module.FragmentModule;


@Singleton
@Component(modules={AndroidSupportInjectionModule.class, ActivityModule.class, FragmentModule.class, AppModule.class})
public interface AppTestComponent extends AppComponent {

    void inject(App app);
}

