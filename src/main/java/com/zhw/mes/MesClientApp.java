package com.zhw.mes;

import com.zhw.mes.support.route.AppRouter;
import com.zhw.mes.support.route.ReplaceNodeAppRouter;
import com.zhw.mes.support.route.StageAppRouter;
import com.zhw.mes.support.utils.AppUtil;
import com.zhw.mes.support.utils.AppUtilImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class MesClientApp extends Application {
    private final Logger logger = LoggerFactory.getLogger(MesClientApp.class);
    private static String[] args;
    public static void main(String[] args) {
        MesClientApp.args = args;
        launch(MesClientApp.class,args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new FXMLLoader().load(getClass().getResourceAsStream("/ui/splash.fxml"));
        Scene scene = new Scene(pane);
        Stage splash = new Stage(StageStyle.UNDECORATED);
        splash.setScene(scene);
        splash.show();

        CompletableFuture.supplyAsync(()->startSpringBoot())
                .whenComplete((ctx,error)->{
                    if(error!=null){
                        Platform.runLater(()->alertError(error));
                    }else{
                        Platform.runLater(()->startMainUI(ctx));
                    }
                }).thenAccept((ctx)->{
                    Platform.runLater(()-> splash.close());
                });


    }

    private void alertError(Throwable error) {
        logger.error("启动失败",error);
    }

    private void startMainUI(ApplicationContext ctx) {
        AppUtilImpl appUtil = ctx.getBean(AppUtilImpl.class);

        Stage mainStage = new Stage();
        String fxmlPath = "/ui/index.fxml";
        Pane rootNode = appUtil.loadFXML(fxmlPath);
        Scene rootScene = new Scene(rootNode);

        appUtil.setRootScene(rootScene);
        appUtil.setRootNode(rootNode);

        mainStage.setScene(rootScene);
        mainStage.setTitle("hello");
        //mainStage.setMaximized(true);
        mainStage.show();
    }

    private ApplicationContext startSpringBoot() {
        Path path = Paths.get("fb").toAbsolutePath();
        logger.info("Attempting to set jna.library.path to: " + path);
        System.setProperty("jna.library.path", path.toString());

        SpringApplication application = new SpringApplication(MesClientApp.class);
        ApplicationContext ctx = application.run(MesClientApp.args);
        return ctx;
    }

    //apputils ,持有全局节点,及装配FxmlLoader所需要的信息
    @Bean
    public AppUtil appUtil(ApplicationContext applicationContext){
        return new AppUtilImpl(applicationContext);
    }

    //路由
    @Bean
    public AppRouter appRouter(){
        //return new StageAppRouter();
        return new ReplaceNodeAppRouter();
    }
}
