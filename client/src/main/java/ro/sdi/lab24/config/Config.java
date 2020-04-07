package ro.sdi.lab24.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.sdi.lab24.controller.ClientController;
import ro.sdi.lab24.controller.ClientControllerImpl;
import ro.sdi.lab24.controller.Controller;
import ro.sdi.lab24.controller.ControllerImpl;
import ro.sdi.lab24.controller.FutureClientController;
import ro.sdi.lab24.controller.FutureController;
import ro.sdi.lab24.controller.FutureMovieController;
import ro.sdi.lab24.controller.FutureRentalController;
import ro.sdi.lab24.controller.MovieController;
import ro.sdi.lab24.controller.MovieControllerImpl;
import ro.sdi.lab24.controller.RentalController;
import ro.sdi.lab24.controller.RentalControllerImpl;
import ro.sdi.lab24.networking.ServerInformation;
import ro.sdi.lab24.view.Console;
import ro.sdi.lab24.view.ResponseBuffer;

@Configuration
public class Config {
    @Bean
    Console console() {
        return new Console();
    }

    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    FutureController futureController() {
        return new ControllerImpl();
    }

    @Bean
    FutureClientController futureClientController() {
        return new ClientControllerImpl();
    }

    @Bean
    FutureMovieController futureMovieController() {
        return new MovieControllerImpl();
    }

    @Bean
    FutureRentalController futureRentalController() {
        return new RentalControllerImpl();
    }

    @Bean
    DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    }

    @Bean
    ResponseBuffer responseBuffer() {
        return new ResponseBuffer();
    }

    @Bean
    RmiProxyFactoryBean rmiControllerProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(Controller.class);
        String url = String.format("rmi://localhost:%d/Controller", ServerInformation.PORT);
        rmiProxyFactoryBean.setServiceUrl(url);
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiClientProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(ClientController.class);
        String url = String.format("rmi://localhost:%d/ClientController", ServerInformation.PORT);
        rmiProxyFactoryBean.setServiceUrl(url);
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiMovieProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(MovieController.class);
        String url = String.format("rmi://localhost:%d/MovieController", ServerInformation.PORT);
        rmiProxyFactoryBean.setServiceUrl(url);
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiRentalProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(RentalController.class);
        String url = String.format("rmi://localhost:%d/RentalController", ServerInformation.PORT);
        rmiProxyFactoryBean.setServiceUrl(url);
        return rmiProxyFactoryBean;
    }
}
