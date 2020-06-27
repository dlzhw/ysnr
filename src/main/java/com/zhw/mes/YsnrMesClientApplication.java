package com.zhw.mes;

import com.zhw.mes.support.route.AppRouter;
import com.zhw.mes.support.route.StageAppRouter;
import com.zhw.mes.support.utils.AppUtil;
import com.zhw.mes.support.utils.AppUtilImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class YsnrMesClientApplication extends Application{
    private static final Logger logger = LoggerFactory.getLogger(YsnrMesClientApplication.class);
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {

        SpringApplication.run(YsnrMesClientApplication.class, args);
    }

    @Bean
    @DependsOn("appUtil")
    public CommandLineRunner startMesClient(){
        return new CommandLineRunner(){
            @Override
            public void run(String... args) throws Exception {

                launch(YsnrMesClientApplication.class, args);
            }
        };
    }

    //apputils ,持有全局节点,及装配FxmlLoader所需要的信息
    @Bean
    public AppUtil appUtil(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        return new AppUtilImpl(applicationContext);
    }

    //路由
    @Bean
    public AppRouter appRouter(){
        return new StageAppRouter();
        //return new ReplaceNodeAppRouter();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Path path = Paths.get("fb").toAbsolutePath();
        logger.info("Attempting to set jna.library.path to: " + path);
        System.setProperty("jna.library.path", path.toString());


        AppUtil appUtil = applicationContext.getBean(AppUtil.class);
        AppUtilImpl impl = (AppUtilImpl) appUtil;

        String fxmlPath = "/ui/index.fxml";
        Pane rootNode = AppUtil.loadFXML(fxmlPath);
        Scene rootScene = new Scene(rootNode);

        impl.setRootScene(rootScene);
        impl.setRootNode(rootNode);

        primaryStage.setScene(rootScene);
        primaryStage.setTitle("hello");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
