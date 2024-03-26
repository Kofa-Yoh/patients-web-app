package org.kotkina.patientswebapp;

import org.kotkina.patientswebapp.servers.Server;
import org.kotkina.patientswebapp.servers.TomcatServer;

public class Main {

    public static void main(String[] args) {
        Server app = new TomcatServer();
        app.run(args);
    }
}
