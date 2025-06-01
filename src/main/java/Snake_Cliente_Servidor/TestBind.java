/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package Snake_Cliente_Servidor;

import java.net.ServerSocket;

public class TestBind {
    public static void main(String[] args) {
        int puertoTest = 60000; 
        try {
            System.out.println("Probando bind en puerto " + puertoTest);
            ServerSocket ss = new ServerSocket(puertoTest);
            System.out.println("Bind exitoso en puerto " + puertoTest);
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

