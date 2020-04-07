package ro.sdi.lab.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.sdi.lab.client.controller.FutureClientController;
import ro.sdi.lab.client.controller.FutureMovieController;
import ro.sdi.lab.client.controller.FutureRentalController;
import ro.sdi.lab.common.controller.ClientController;
import ro.sdi.lab.client.controller.ClientControllerImpl;
import ro.sdi.lab.common.controller.Controller;
import ro.sdi.lab.client.controller.ControllerImpl;
import ro.sdi.lab.client.controller.FutureController;
import ro.sdi.lab.common.controller.MovieController;
import ro.sdi.lab.client.controller.MovieControllerImpl;
import ro.sdi.lab.common.controller.RentalController;
import ro.sdi.lab.client.controller.RentalControllerImpl;
import ro.sdi.lab.common.networking.ServerInformation;
import ro.sdi.lab.client.view.Console;
import ro.sdi.lab.client.view.ResponseBuffer;

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
