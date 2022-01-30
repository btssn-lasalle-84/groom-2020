package com.example.groom;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @file IHMConnexion.java
 * @brief Déclaration de la classe IHMConnexion
 * @author Grégory Eyraud
 */

/**
 * @class IHMConnexion
 * @brief Déclaration de la classe IHMConnexion
 */
public class IHMConnexion extends AppCompatActivity implements View.OnClickListener {
    /**
     * Constantes
     */

    private static final String TAG = "IHMConnexion"; //!< TAG pour les logs
    private static final int ACTION_GROOM = 1; //!< Code de requête lors du début et de la fin de l'activité IHMGroom

    /**
     * Ressources IHM
     */
    private Button boutonConnexion; //!< Le bouton pour se connecter à l'appareil Groom
    private Button boutonAjoutOccupant; //!< Le bouton pour ajouter un occupant
    private Button boutonRetraitOccupant; //!< Le bouton pour retirer un occupant
    private Spinner listeGroom; //!< Liste déroulante des appareils bluetooth
    private Spinner listeOccupant; //!< Liste déroulante des occupants
    private AlertDialog.Builder ajoutOccupant; //!< Le builder qui permet de créer une fenêtre de dialogue d'ajout d'occupant
    private AlertDialog.Builder retraitOccupant; //!< Le builder qui permet de créer une fenêtre de dialogue de retrait d'occupant
    private EditText nomOccupant; //!< Le champ de texte pour écrire le nom de l'occupant
    private EditText prenomOccupant; //!< Le champ de texte pour écrire le prenom de l'occupant
    private EditText fonctionOccupant; //!< Le champ de texte pour écrire la fonction de l'occupnt

    /**
     * Attributs
     */
    private Groom groom = null; //!< L'objet groom
    private BluetoothAdapter bluetoothAdapter; //!< L'objet BluetoothAdapter
    private Set<BluetoothDevice> devices; //!< Conteneur qui liste les appareils bluetooth disponibles sans doublons
    private List<String> listeNomsAppareilsBluetooth; //!< Conteneur qui liste les noms des appareils bluetooth disponibles
    private List<String> listeOccupants; //!< Conteneur qui liste les occupants
    private ArrayAdapter<String> adapterGroom; //!< Adaptateur pour mettre la liste de noms des appareils dans la liste déroulante listeGroom
    private ArrayAdapter<String> adapterOccupant; //!< Adaptateur pour mettre la liste des occupants dnas la liste déroulante listeOccupant
    private List<BluetoothDevice> listeAppareilsBluetooth; //!< Conteneur qui liste les appareils bluetooth disponibles
    private Toast toast; //!< Le toast qui permet d'afficher des informations à l'utilisateur
    private Occupants occupants = null;
    private Preferences preferences = null;

    /**
     * @param savedInstanceState
     * @brief Méthode appelée à la création de l'activité IHMConnexion
     * @fn IHMConnexion::onCreate(Bundle savedInstanceState)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        groom = new Groom("Eyraud", "Grégory", "étudiant", "Libre", true, false);
        occupants = new Occupants(this);
        preferences = new Preferences(this);

        initialiserRessourcesIHM();
        activerBluetooth();

        selectionnerGroom();
        selectionnerOccupant();
    }

    /**
     * @brief Méthode permet de sélectionner un portier Groom Bluetooth dans la liste
     */
    private void selectionnerGroom() {
        listeGroom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Log.v(TAG, "onItemSelected()");
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                for (int i = 0; i < listeAppareilsBluetooth.size(); i++)
                {
                    if (listeAppareilsBluetooth.get(i).getName().equals(listeNomsAppareilsBluetooth.get(position)))
                    {
                        Log.v(TAG, "onItemSelected() " + listeAppareilsBluetooth.get(i).getName());
                        groom.setNomDevice(listeAppareilsBluetooth.get(i).getName());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    /**
     * @brief Méthode permet de sélectionner un occupant dans la liste
     */
    private void selectionnerOccupant() {
        listeOccupant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Log.v(TAG, "onItemSelected()" + "Occupant choisi : " + listeOccupants.get(position));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                String identite[] = listeOccupants.get(position).split(" ");
                groom.setOccupant(occupants.getOccupant(identite[0]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    /**
     * @brief Méthode qui active le Bluetooth de l'appareil
     * @fn IHMConnexion::activerBluetooth()
     */
    public void activerBluetooth()
    {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth non activé !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (!bluetoothAdapter.isEnabled())
            {
                Toast.makeText(getApplicationContext(), "Bluetooth non activé !", Toast.LENGTH_SHORT).show();
                bluetoothAdapter.enable();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Bluetooth activé", Toast.LENGTH_SHORT).show();
            }
        }

        lancerRecherchePeripherique();
    }

    /**
     * @brief Méthode qui lance la recherche de périphérique
     * @fn IHMConnexion::lancerRecherchePeripherique()
     */
    public void lancerRecherchePeripherique()
    {
        devices = bluetoothAdapter.getBondedDevices();

        //if(!(preferences.getPreference().getAppareilGroom().equals("")))
        //{
            //listeNomsAppareilsBluetooth.add(preferences.getPreference().getAppareilGroom());
        //}

        //Log.v(TAG, preferences.getPreference().toString());

        for (BluetoothDevice bluetoothDevice : devices)
        {
            if(bluetoothDevice.getName().startsWith("groom"))
            {
                listeAppareilsBluetooth.add(bluetoothDevice);
                listeNomsAppareilsBluetooth.add(bluetoothDevice.getName());
            }
        }

        listerOccupant();
    }

    private void listerOccupant()
    {
        List<Occupant> liste = occupants.getListe();
        for(int i = 0; i<liste.size(); i++)
        {
            listeOccupants.add(liste.get(i).getNom() + " " + liste.get(i).getPrenom() + " " + liste.get(i).getFonction());
        }
        initialiserListeDeroulante();
    }

    /**
     * @brief Méthode qui initialise les ressources du layout de l'activité IHMConnexion
     * @fn IHMConnexion::initialiserRessourcesIHM()
     */
    private void initialiserRessourcesIHM()
    {
        Log.d(TAG, "initialiserRessourcesIHM()");

        boutonConnexion = findViewById(R.id.bouttonConnexion);
        boutonAjoutOccupant = findViewById(R.id.bouttonAjoutOccupant);
        boutonRetraitOccupant = findViewById(R.id.bouttonRetraitOccupant);

        boutonConnexion.setOnClickListener(this);
        boutonAjoutOccupant.setOnClickListener(this);
        boutonRetraitOccupant.setOnClickListener(this);

        listeGroom = findViewById(R.id.listeGroom);
        listeOccupant = findViewById(R.id.listeOccupant);

        listeNomsAppareilsBluetooth = new ArrayList<String>();
        listeAppareilsBluetooth = new ArrayList<BluetoothDevice>();
        listeOccupants = new ArrayList<String>();

        initialiserAjoutOccupant();
        initialiserRetraitOccupant();
    }

    /**
     * @brief Méthode qui initialise la liste déroulante d'appareils Bluetooth appairés
     * @fn IHMConnexion::initialiserListeDeroulante()
     */
    private void initialiserListeDeroulante()
    {
        adapterGroom = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listeNomsAppareilsBluetooth);
        adapterGroom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeGroom.setAdapter(adapterGroom);

        adapterOccupant = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listeOccupants);
        adapterOccupant.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeOccupant.setAdapter(adapterOccupant);
    }

    /**
     * @param requestCode le code de requête
     * @param resultCode  le code de résultat
     * @param intent      l'objet Intent utilisé pour envoyer l'objet Groom
     * @brief Méthode appelée à la fin de l'activité lancée et récupère l'objet groom envoyé
     * @fn IHMConnexion::onActivityResult(int requestCode, int resultCode, Intent intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == ACTION_GROOM && resultCode == RESULT_OK)
        {
            groom = (Groom) intent.getSerializableExtra("Groom");
            Log.v(TAG, "Disponibilité = " + groom.getDisponibilite());
            Log.v(TAG, "Sonnette = " + groom.getModeSonnette());
        }
    }

    @Override
    public void onClick(View element)
    {
        switch (element.getId())
        {
            case R.id.bouttonAjoutOccupant:
                //afficherToast("Ajout Occupant");
                ajoutOccupant.show();
                break;

            case R.id.bouttonRetraitOccupant:
                //afficherToast("Retrait Occupant");
                retraitOccupant.show();
                break;

            case R.id.bouttonConnexion:
                afficherToast("Connexion");

                Intent intent = new Intent(IHMConnexion.this, IHMGroom.class);
                intent.putExtra("Groom", (Serializable) groom);
                startActivityForResult(intent, ACTION_GROOM);
                /*if(preferences.getPreference() == null)
                    preferences.inserer(groom.getNomDevice(), listeOccupant.getId());
                else
                    preferences.modifier(0, groom.getNomDevice(), listeOccupant.getId());*/
                break;
        }
    }

    /**
     * @param message le message à afficher
     * @brief Méthode appelée pour donner des informations supplémentaires à l'utilisateur
     * @fn IHMConnexion::afficherToast(String message)
     */
    private void afficherToast(String message)
    {
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * @brief Méthode appelée pour initialiser la boite de dialogue personnalisé pour l'ajout d'un occupant
     * @fn IHMConnexion::initialiserAjoutOccupant()
     */
    private void initialiserAjoutOccupant()
    {
        ajoutOccupant = new AlertDialog.Builder(this);

        ajoutOccupant.setMessage("Veuillez saisir vos informations : ");
        ajoutOccupant.setView(R.layout.ajout_occupant);
        ajoutOccupant.setPositiveButton("Ajouter", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                nomOccupant = (EditText) ((AlertDialog) dialog).findViewById(R.id.nomOccupant);
                prenomOccupant = (EditText) ((AlertDialog) dialog).findViewById(R.id.prenomOccupant);
                fonctionOccupant = (EditText) ((AlertDialog) dialog).findViewById(R.id.fonctionOccupant);
                Log.v(TAG, "Occupant ajouté :" + "Nom = " + nomOccupant.getText().toString() + " "
                        + "Prenom = " + prenomOccupant.getText().toString() + " "
                        + "Fonction = " + fonctionOccupant.getText().toString());

                adapterOccupant.add(nomOccupant.getText().toString() + " " + prenomOccupant.getText().toString() + " " + fonctionOccupant.getText().toString());

                occupants.inserer(nomOccupant.getText().toString(), prenomOccupant.getText().toString(), fonctionOccupant.getText().toString());
            }
        });
        ajoutOccupant.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.v(TAG, "Ajout annulé");
            }
        });
    }

    /**
     * @brief Méthode appelée pour initialiser la boite de dialogue personnalisé pour le retrait d'un occupant
     * @fn IHMConnexion::initialiserAjoutOccupant()
     */
    private void initialiserRetraitOccupant()
    {
        retraitOccupant = new AlertDialog.Builder(this);
        retraitOccupant.setMessage("Vous êtes sur le point de supprimer l'occupant choisit.\r\n\r\n" + "Êtes-vous sûr de vouloir le supprimer ?");
        retraitOccupant.setPositiveButton("Supprimer", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.v(TAG, "Supression Occupant");
                occupants.supprimer(groom.getOccupant().getId());
                adapterOccupant.remove(groom.getOccupant().getNom() + " " + groom.getOccupant().getPrenom() + " " + groom.getOccupant().getFonction());
                listeOccupant.setAdapter(adapterOccupant);
            }
        });
        retraitOccupant.setNegativeButton("Annuler", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Log.v(TAG, "Suppression annulée");
            }
        });
    }
}
