#ifndef CONTROLE_H
#define CONTROLE_H

#include <QObject>
#include <QDateTime>
#include <QString>
#include "communicationbluetooth.h"
#include "ihmgroom.h"
#include "messagepersonnalise.h"

/**
 * @brief
 * Classe assurant le controle et la validé de l'envoie et réception des trames
 * @class Controle controle.h "controle.h"
 */

class IhmGroom;
class CommunicationBluetooth;
class MessagePersonnalise;


class Controle : public QObject
{
    Q_OBJECT

    private:
        CommunicationBluetooth *communicationBluetooth;     /** Objet servant à récuperer les trame du Bluetooth */
        MessagePersonnalise *messagePersonnalise;           /** Objet servant à récuperer les messages personnalisé */
        IhmGroom *ui;                                       /** L'interface utilisateur */

        QString trameGroom;                                 /** Trame provenant de la liaison Bluetooth et à envoyer */
        QString trameAffichage;                             /** Trame à envoyer à la liaison série */
        QString trameMsgPerso;                              /** Trame à envoyer à la liaison série */


        /**
         * @brief
         * Regarde le checksum de la trameGroom
         * @fn verifierChecksum
         * @return retourne un true ou false
         */
        bool verifierChecksum(QString trameGroom);
        /**
         * @brief
         * Décode la trame Groom
         * @fn decoderTrameGroom
         */
        void decoderTrameGroom(QString trameGroom);
        /**
         * @brief
         * coder la trame Groom
         * @fn coderTrameGroom
         */
        void coderTrameGroom(QString trameGroom);
        /**
         * @brief
         * coder la trame Affichage
         * @fn coderTrameAffichage
         */
        void coderTrameAffichage(QString trameAffichage);
        /**
         * @brief
         * coder la trame Msg Perso
         * @fn coderTrameMsgPerso
         */
        void coderTrameMsgPerso(QString trameMsgPerso);


        int etatUtilisateur;                                /** Attribut de l'état de l'utilisateur */
        bool etatSonnette;                                  /** Attribut de l'état de la sonnette */
        bool presence;                                      /** Attribut de l'état du capteur de présence */

        QString nom;                                        /** Attribut du nom de l'utilisateur */
        QString prenom;                                     /** Attribut du prénom de l'utilisateur  */
        QString fonction;                                   /** Attribut de la fonction de l'utilisateur */



    public:
        /**
         * @brief
         * Constructeur classe Controle
         * @fn Controle
         * @param parent
         */
        Controle(QObject *parent = nullptr);
        /**
         * @brief
         * Destructeur classe Controle
         * @fn ~Controle
         * @param parent
         */
        ~Controle();
        /**
         * @brief
         * modifie l'état de la sonnette
         * @fn setEtatSonnette
         * @param etatSonnette
         */
        void setEtatSonnette(bool newEtatSonnette);
        /**
         * @brief
         * retroune l'état de la sonnette
         * @fn getEtatSonnette
         * @return etatSonnette
         */
        bool getEtatSonnette();
        /**
         * @brief
         * modifie l'état de la sonnette
         * @fn setEtatUtilisateur
         * @param etatUtilisateur
         */
        void setEtatUtilisateur(int newEtatUtilisateur);
        /**
         * @brief
         * retroune l'état de la sonnette
         * @fn getEtatUtilisateur
         * @return etatUtilisateur
         */
        int getEtatUtilisateur();
        /**
         * @brief
         * modifie l'état de la présence
         * @fn setPresence
         * @param presence
         */
        void setPresence(bool newPresence);
        /**
         * @brief
         * retroune l'état de la sonnette
         * @fn getPresence
         * @return presence
         */
        bool getPresence();

        /**
         * @brief
         * modifie le nom
         * @fn setNom
         * @param nom
         */
        void setNom(QString newNom);
        /**
         * @brief
         * retroune le nom
         * @fn getNom
         * @return nom
         */
        QString getNom();
        /**
         * @brief
         * modifie le prénom
         * @fn setPrénom
         * @param prenom
         */
        void setPrenom(QString newPrenom);
        /**
         * @brief
         * retroune le prenom
         * @fn getPrenom
         * @return prenom
         */
        QString getPrenom();
        /**
         * @brief
         * modifie la fonction
         * @fn setFonction
         * @param fonction
         */
        void setFonction(QString newFonction);
        /**
         * @brief
         * retroune la fonction
         * @fn getFonction
         * @return fonction
         */
        QString getFonction();


    public slots:

};

#endif // CONTROLE_H
