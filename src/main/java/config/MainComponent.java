package config;

import gui.MainWindow;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {
        ModuleComponent.class
})

@Singleton
public interface MainComponent {
    MainWindow createMainWindow();
}
