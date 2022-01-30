package com.example.groom;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Set;

/**
 * @file IHMGroom.java
 * @brief Déclaration de la classe IHMGroom
 * @author Grégory Eyraud
 */

/**
 * @class IHMGroom
 * @brief Déclaration de la classe IHMGroom
 */
public class IHMGroom extends AppCompatActivity implements View.OnClickListener {
    /**
     * Constantes
     */
    private static final String TAG = "IHMGroom"; //!< TAG pour les logs

    /**
     * Ressources IHM
     */
    private Button boutonEntrez;
    private Button boutonPerso; //!< Le bouton pour envoyer un message personnalisé
    private Button boutonLibre; //!< Le bouton pour définir sa disponibilité en Libre
    private Button boutonAbsent; //!< Le bouton pour définir sa disponibilité en Absent
    private Button boutonOccupe; //!< Le bouton pour définir sa disponibilité en Occupé
    private Button boutonModeSonnette; //!< Le bouton pour activer/désactiver la sonnette
    private EditText messagePerso; //!< Le champ de texte pour écrire son message personnalisé
    private Button boutonDeconnexion; //!< Le bouton pour se déconnecter
    private TextView disponibiliteActuelle; //!< Le texte qui affiche la dernière disponiblité définie
    private ImageView imageNotification;
    private AlertDialog.Builder saisieMessagePerso; //!< Le builder qui permet de créer une fenêtre de dialogue de saisie personnalisé
    private Toast toast; //!< Le toast qui permet d'afficher des informations à l'utilisateur

    /**
     * Attributs
     */
    private Groom groom = null; //!< L'objet groom connecté
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //!< L'objet Bluetooth
    private Communication communication = null; //!< l'objet communication pour communiquer avec le portier groom

    /**
     * @brief Méthode appelée à la création de l'activité IHMGroom
     *
     * @fn IHMGroom::onCreate(Bundle savedInstanceState)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groom);

        Intent intent = getIntent();
        groom = (Groom) intent.getSerializableExtra("Groom");
        if (groom != null)
        {
            Log.d(TAG, "Groom : " + groom.getOccupant().getNom() + " " + groom.getOccupant().getPrenom() + " " + groom.getDisponibilite() + " " + groom.getNomDevice());
        }

        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        //Log.d(TAG, "Nb BluetoothDevice " + devices.size());
        for (BluetoothDevice bluetoothDevice : devices)
        {
            //Log.d(TAG, "BluetoothDevice " + bluetoothDevice.getName() + " [" + bluetoothDevice.getAddress() + "]");
            if(bluetoothDevice.getName().equals(groom.getNomDevice()))
            {
                Log.d(TAG, "BluetoothDevice Groom trouvé : " + bluetoothDevice.getName() + " [" + bluetoothDevice.getAddress() + "]");
                communication = new Communication(bluetoothDevice, handler);
            }
        }

        if(communication != null)
        {
            communication.connecter();
        }

        initialiserRessourcesIHM();
        initialiserSaisieMessagePerso();
    }

    /**
     * @brief Méthode appelée au lancement de l'activité IHMGroom
     *
     * @fn IHMGroom::onStart()
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        if (groom != null)
        {
            disponibiliteActuelle.setText(groom.getDisponibilite());
            disponibiliteActuelle.setTextColor(Color.parseColor(groom.getCouleurDisponibilite()));
        }
    }

    /**
     * @brief Méthode appelée pour initialiser les différentes ressources nécessaire à l'affichage de l'IHM
     *
     * @fn IHMGroom::initialiserRessourcesIHM()
     */
    private void initialiserRessourcesIHM()
    {
        // Les boutons
        boutonPerso = findViewById(R.id.bouttonMessagePersonnalise);
        boutonEntrez = findViewById(R.id.boutonEntrez);
        boutonLibre = findViewById(R.id.boutonLibre);
        boutonAbsent = findViewById(R.id.boutonAbsent);
        boutonOccupe = findViewById(R.id.boutonOccupe);
        boutonModeSonnette = findViewById(R.id.boutonSonnette);
        boutonDeconnexion = findViewById(R.id.boutonDeconnexion);
        imageNotification = findViewById(R.id.imageNotification);

        boutonPerso.setOnClickListener(this);
        boutonEntrez.setOnClickListener(this);
        boutonLibre.setOnClickListener(this);
        boutonAbsent.setOnClickListener(this);
        boutonOccupe.setOnClickListener(this);
        boutonModeSonnette.setOnClickListener(this);
        boutonDeconnexion.setOnClickListener(this);
        imageNotification.setOnClickListener(this);

        disponibiliteActuelle = (TextView) findViewById(R.id.disponibiliteActuelle);
        imageNotification.setVisibility(View.INVISIBLE);

        verifierModeSonnette();
    }

    /**
     * @brief Méthode appelée pour initialiser la boite de dialogue personnalisé pour la saisie d'un message personnalisé
     *
     * @fn IHMGroom::initialiserSaisieMessagePerso()
     */
    private void initialiserSaisieMessagePerso()
    {
        saisieMessagePerso = new AlertDialog.Builder(this);

        saisieMessagePerso.setMessage("Veuillez saisir votre message :");
        saisieMessagePerso.setView(R.layout.saisie_message_perso);
        saisieMessagePerso.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                messagePerso = (EditText) ((AlertDialog) dialog).findViewById(R.id.saisieMessagePerso);
                Log.v(TAG, "Saisie : " + messagePerso.getText().toString());
                communication.envoyer("$MSGPERSO;" + messagePerso.getText() + "\r\n");
            }
        });
        saisieMessagePerso.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "Saisie annulée");
            }
        });
    }

    /**
     * @brief Méthode appelé au click d'un bouton et appelle une méthode celon le bouton qui a été cliqué
     *
     * @fn IHMGroom::onClick(View element)
     * @param element l'élément cliqué
     */
    @Override
    public void onClick(View element)
    {
        switch (element.getId()) {
            case R.id.bouttonMessagePersonnalise:
                saisieMessagePerso.show();
                break;

            case R.id.boutonEntrez:
                communication.envoyer("$GROOM;3;" + boolToInt(groom.getModeSonnette()) + ";" + boolToInt(groom.getDetectionPresence()) + "\r\n");
                imageNotification.setVisibility(View.INVISIBLE);
                break;

            case R.id.boutonLibre:
                communication.envoyer("$GROOM;0;" + boolToInt(groom.getModeSonnette()) + ";" + boolToInt(groom.getDetectionPresence()) + "\r\n");
                if (groom != null)
                    groom.setDisponibilite("Libre");
                break;

            case R.id.boutonAbsent:
                communication.envoyer("$GROOM;1;" + boolToInt(groom.getModeSonnette()) + ";" + boolToInt(groom.getDetectionPresence()) + "\r\n");
                if (groom != null)
                    groom.setDisponibilite("Absent");
                break;

            case R.id.boutonOccupe:
                communication.envoyer("$GROOM;2;" + boolToInt(groom.getModeSonnette()) + ";" + boolToInt(groom.getDetectionPresence()) + "\r\n");
                if (groom != null)
                    groom.setDisponibilite("Occupé");
                break;

            case R.id.boutonSonnette:
                if (boutonModeSonnette.getText().equals(getString(R.string.desactiverSonnette)))
                {
                    if (groom != null)
                    {
                        groom.setModeSonnette(false);
                        communication.envoyer("$GROOM;" + groom.getDisponibiliteToInt() + ";" + boolToInt(groom.getModeSonnette()) + ";" + boolToInt(groom.getDetectionPresence()) + "\r\n");
                        boutonModeSonnette.setText(R.string.activerSonnette);
                    }
                }
                else
                {
                    if (groom != null)
                    {
                        groom.setModeSonnette(true);
                        communication.envoyer("$GROOM;" + groom.getDisponibiliteToInt() + ";" + boolToInt(groom.getModeSonnette()) + ";" + boolToInt(groom.getDetectionPresence()) + "\r\n");
                        boutonModeSonnette.setText(R.string.desactiverSonnette);
                    }
                }
                break;

            case R.id.boutonDeconnexion:
                afficherToast("Déconnexion");
                communication.deconnecter();
                finish();
                break;

            case R.id.imageNotification:
                imageNotification.setVisibility(View.INVISIBLE);
                break;
        }

        if (groom != null)
            disponibiliteActuelle.setText(groom.getDisponibilite());
    }

    /**
     * @brief Méthode appelée pour donner des informations supplémentaires à l'utilisateur
     *
     * @fn IHMGroom::afficherToast(String message)
     * @param message le message à afficher
     */
    private void afficherToast(String message)
    {
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * @brief Méthode appelée à la fin de l'activité
     *
     * @fn IHMGroom::finish()
     */
    @Override
    public void finish()
    {
        if(communication != null)
            communication.deconnecter();
        Intent data = new Intent();
        data.putExtra("Groom", groom);
        setResult(RESULT_OK, data);
        super.finish();
    }

    /**
     * @brief Méthode appelée pour vérifier le mode de sonnette et changer le texte du boutton
     *
     * @fn IHMGroom::verifierModeSonnette()
     */
    private void verifierModeSonnette()
    {
        if(groom.getModeSonnette())
        {
            boutonModeSonnette.setText(R.string.desactiverSonnette);
        }
        else
        {
            boutonModeSonnette.setText(R.string.activerSonnette);
        }
    }

    /**
     * @brief objet Handler utiliser pour la reception du code de retour de la communication
     */
    final private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            switch(msg.what)
            {
                case Communication.CODE_CONNEXION:
                    Log.v(TAG, "Groom connecté");
                    communication.envoyer("$AFFICHAGE;" + groom.getOccupant().getNom() + ";" + groom.getOccupant().getPrenom() + ";" + groom.getOccupant().getFonction() + "\r\n");
                    break;
                case Communication.CODE_RECEPTION:
                    Log.v(TAG, "Trame reçue " + msg.obj);
                    String trame[] = msg.obj.toString().split(";");
                    decoderTrameRecue(trame);
                    break;
                case Communication.CODE_DECONNEXION:
                    Log.v(TAG, "Groom déconnecté");
                    break;
            }
        }
    };

    /**
     * @brief Méthode qui permet de décoder la trame reçue
     *
     * @param trame la trame à décoder
     *
     * @fn IHMGroom::decoderTrameRecue(String trame[])
     */
    private void decoderTrameRecue(String trame[])
    {
        if (trame[0].equals("$GROOM"))
        {
            groom.setDisponibiliteToInt(Integer.parseInt(trame[1]));
            disponibiliteActuelle.setText(groom.getDisponibilite());
            disponibiliteActuelle.setTextColor(Color.parseColor(groom.getCouleurDisponibilite()));
            if(trame[2].equals("1"))
            {
                creerNotification("Quelqu'un vient de sonner à votre porte", 1);
            }
            if(trame[3].equals("1"))
            {
                creerNotification("Une personne attend devant votre bureau", 2);
            }
        }
    }

    /**
     * @brief Méthode qui permet de créer une notification pour avertir l'utilisateur si quelqu'un à sonné et/ou si quelqu'un est présent devant la porte
     *
     * @param texte le texte de la notification
     * @param id unique à la notification
     *
     * @fn IHMGroom::creerNotification(String texte, int id)
     */
    private void creerNotification(String texte, int id)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelId")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("GrOOm")
                .setContentText(texte)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationSonnette = NotificationManagerCompat.from(this);
        notificationSonnette.notify(id, builder.build());
        Log.v(TAG, "Notification Groom : " + texte);
        imageNotification.setVisibility(View.VISIBLE);
    }

    /**
     * @brief Méthode qui convertit un bool en un int
     *
     * @param b le booléen à convertir
     * @return int 1 pour true et 0 pour false
     *
     * @fn IHMGroom::boolToInt(boolean b)
     */
    private int boolToInt(boolean b)
    {
        return b ? 1 : 0;
    }
}
