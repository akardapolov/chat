package config;

import dagger.Module;
import dagger.Provides;
import data.DbSession;
import data.DbSessionInterface;
import gui.BasicFrame;

import javax.inject.Singleton;

@Module
public class ModuleComponent {
    @Provides @Singleton public BasicFrame getBasicFrame() { return new BasicFrame(); }
    @Provides @Singleton public DbSessionInterface mapDbSessionInterfaceAndImpl(){ return new DbSession(); }
}
