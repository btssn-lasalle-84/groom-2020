package com.example.groom;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @file Communication.java
 * @brief Déclaration de la classe Communication
 * @author Grégory Eyraud
 */

/**
 * @class Communication
 * @brief Déclaration de la classe Communication
 */
public class Communication extends Thread
{
    /**
     * Constantes
     */
    private static final String TAG = "Communication";  //!< TAG pour les logs
    public final static int CODE_CONNEXION = 0;         //!< Code de connexion
    public final static int CODE_RECEPTION = 1;         //!< Code de réception d'une trame
    public final static int CODE_DECONNEXION = 2;       //!< Code de déconnexion

    /**
     * Attributs
     */
    private BluetoothDevice device; //!< L'objet device
    private String nom;             //!< Le nom
    private String adresse;         //!< L'adresse
    private Handler handler;        //!< L'objet handler
    private BluetoothSocket socket = null;      //!< L'objet socket
    private InputStream receiveStream = null;   //!< L'objet receiveStream
    private OutputStream sendStream = null;     //!< L'objet sendStream
    private TReception tReception;  //!< L'objet tReception

    /**
     * @brief Constructeur de la classe Communication
     *
     * @param device L'appareil où on va se connecter
     * @param handler L'handler
     */
    public Communication(BluetoothDevice device, Handler handler)
    {
        this.handler = handler;
        if (device != null)
        {
            this.device = device;
            this.nom = device.getName();
            this.adresse = device.getAddress();

            try
            {
                socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                receiveStream = socket.getInputStream();
                sendStream = socket.getOutputStream();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            this.device = device;
            this.nom = "Aucun";
            this.adresse = "";
        }

        if(socket != null)
            tReception = new TReception(handler);
        Log.v(TAG, "Communication() : Nom = " + getNom());
    }

    /**
     * @brief Accesseur get du nom
     *
     * @fn Communication::getNom()
     * @return String le nom
     */
    public String getNom()
    {
        return nom;
    }

    /**
     * @brief Accesseur get de l'adresse
     *
     * @fn Communication::getAdresse()
     * @return String l'adresse
     */
    public String getAdresse()
    {
        return adresse;
    }

    /**
     * @brief Accesseur get de l'appareil
     *
     * @fn Communication::getDevice()
     * @return BluetoothDevice l'appareil
     */
    public BluetoothDevice getDevice()
    {
        return device;
    }

    /**
     * @brief Méthode pour se connecter à l'appareil
     *
     * @fn Communication::connecter()
     */
    public void connecter()
    {
        new Thread()
        {
            @Override public void run()
            {
                try
                {
                    socket.connect();

                    Message msg = Message.obtain();
                    msg.what = CODE_CONNEXION;
                    handler.sendMessage(msg);

                    if(tReception != null)
                        tReception.start();
                    Log.v(TAG, "connecter() : Nom = " + getNom());
                }
                catch (IOException e)
                {
                    Log.d(TAG, "Erreur connect()");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * @brief Méthode pour se déconnecter de l'appareil
     *
     * @fn Communication::deconnecter()
     * @return boolean true si déconnecté, false si une erreur a été rencontrée
     */
    public boolean deconnecter()
    {
        try
        {
            tReception.arreter();

            socket.close();

            Message msg = Message.obtain();
            msg.what = CODE_DECONNEXION;
            handler.sendMessage(msg);

            return true;
        }
        catch (IOException e)
        {
            Log.d(TAG, "Erreur close()");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @brief Méthode pour envoyer une trame
     *
     * @param data les données à envoyer
     * @fn Communication::envoyer(String data)
     */
    public void envoyer(String data)
    {
        final String trame = data;

        if(socket == null)
            return;

        new Thread()
        {
            @Override public void run()
            {
                try
                {
                    if(socket.isConnected())
                    {
                        sendStream.write(trame.getBytes());
                        sendStream.flush();
                        Log.d(TAG, "envoyer() trame : " + trame);
                    }
                }
                catch (IOException e)
                {
                    Log.d(TAG, "Erreur write()");
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * @brief Méthode pour attendre avant de passer à la prochaine ligne d'exécution
     * @param millis le temps d'attent en ms
     */
    public void attendre(int millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @class TReception
     * @brief Déclaration de la classe TReception
     */
    private class TReception extends Thread
    {
        /**
         * Attributs
         */
        Handler handlerUI;      //!< L'objet handlerUI
        private boolean fini;   //!< true si la réception est fini, false sinon

        /**
         * @brief Constructeur de la calsse TReception
         *
         * @fn TReception::TReception(Handler h)
         * @param h l'handler
         */
        TReception(Handler h)
        {
            handlerUI = h;
            fini = false;
        }

        /**
         * @brief Méthode qui lance le thread
         *
         * @fn TReception::run()
         */
        @Override
        public void run()
        {
            BufferedReader reception = new BufferedReader(new InputStreamReader(receiveStream));
            while (!fini)
            {
                try
                {
                    String trame = "";
                    if (reception.ready())
                    {
                        trame = reception.readLine();
                    }
                    if (trame.length() > 0)
                    {
                        Log.d(TAG, "run() trame : " + trame);
                        Message msg = Message.obtain();
                        msg.what = Communication.CODE_RECEPTION;
                        msg.obj = trame;
                        handlerUI.sendMessage(msg);
                    }
                }
                catch (IOException e)
                {
                    Log.d(TAG, "Erreur read()");
                    e.printStackTrace();
                }
                try
                {
                    Thread.sleep(250);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        /**
         * @brief Méthode pour arrêter la réception
         *
         * @fn TReception::arreter()
         */
        public void arreter()
        {
            if (!fini)
            {
                fini = true;
            }
            try
            {
                Thread.sleep(250);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}


