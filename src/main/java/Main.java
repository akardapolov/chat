import config.DaggerMainComponent;
import config.MainComponent;
import gui.MainWindow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
        public static void main(String... args) {
                log.info("Start application");

                MainComponent mainComponent = DaggerMainComponent.create();

                // create the main window using DI
                MainWindow mainWindow = mainComponent.createMainWindow();
        }
}
