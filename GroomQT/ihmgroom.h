#ifndef IHMGROOM_H
#define IHMGROOM_H

/**
 * @file ihmgroom.h
 * @brief Déclaration de la classe IhmGroom
 *
 * @author Etienne Valette
 */

#include <QtWidgets>
#include <QIcon>
/**
 * @brief
 * Constante contenant le début de la trame sonde selon le protocole
 * @def NOM_APP
 */
#define NOM_APP "Groom"
/**
 * @brief
 * Constante contenant le numéro 0
 * @def LIBRE
 */
#define LIBRE 0
/**
 * @brief
 * Constante contenant le numéro 1
 * @def ABSENT
 */
#define ABSENT 1
/**
 * @brief
 * Constante contenant le numéro 2
 * @def OCCUPE
 */
#define OCCUPE 2



class CommunicationBluetooth;
class Importation;
class MessagePersonnalise;
class Controle;
class Bdd;

namespace Ui {
class IhmGroom;
}

/**
 * @class IhmGroom
 * @brief Déclaration de la classe IhmGroom
*/
class IhmGroom : public QWidget
{
    Q_OBJECT

public:
    /**
     * @brief
     * Constructeur classe IhmGroom
     * @fn IhmGroom
     * @param parent
     */
    explicit IhmGroom(QWidget *parent = nullptr);
    /**
     * @brief
     * Constructeur classe ~IhmGroom
     * @fn ~IhmGroom
     * @param parent
     */
    ~IhmGroom();

protected:
    /**
     * @brief
     * Constructeur classe ~IhmGroom
     * @fn ~IhmGroom
     * @param parent
     */
    void closeEvent(QCloseEvent *event);

private:

    Ui::IhmGroom *ui;                               //!< L'interface utilisateur
    CommunicationBluetooth *communicationBluetooth; //!< L'interface de la Configuration
    Importation *ihmImportation;                    //!< L'interface de l'Importation
    MessagePersonnalise *messagePersonnalise;       //!< L'interface du Message Personnalisé
    Controle *controle;                             /** Objet servant a controller les trame reçues et envoyés */
    Bdd *bdd;


    QSystemTrayIcon *iconeSysteme;                  //!< L'icône de l'application pour la barre système
    QMenu *menuIconeSysteme;                        //!< Le menu de l'application
    QAction *actionMinimiser;                       //!< L'action minimiser l'application
    QAction *actionMaximiser;                       //!< L'action maximiser l'application
    QAction *actionRestaurer;                       //!< L'action restaurer l'application
    QAction *actionQuitter;                         //!< L'action quitter l'application
    bool etatInitialIconeSysteme;                   //!< Booléen indiquant si c'est la première demande Quitter
    /**
     * @brief
     * Affichage dans barre des taches
     * @fn initialiserIconeSysteme
     */
    void initialiserIconeSysteme();

public slots:
    /**
     * @brief
     * Affichage des notification
     * @fn afficherNotification
     */
    void afficherNotification(QString titre, QString message, int duree=1000);
    /**
     * @brief
     * Acquitter les notification
     * @fn acquitterNotification
     */
    void acquitterNotification();

    // Test
    void testerNotification();
    /**
     * @brief
     * Inverse l'image de la sonnette
     * @fn inversionImageSonnette
     */
    void inversionImageSonnette();
    /**
     * @brief
     * change le nom sur l'ihm
     * @fn changementNom
     */
    void changementNom();
    /**
     * @brief
     * change le prenom sur l'ihm
     * @fn changementPrenom
     */
    void changementPrenom();
    /**
     * @brief
     * change la focntion sur l'ihm
     * @fn changementFonction
     */
    void changementFonction();

private slots:
    /**
     * @brief
     * affiche l'ihm communication bluetooth
     * @fn on_pushButton_6_clicked
     */
    void on_pushButton_6_clicked();
    /**
     * @brief
     * affiche l'ihm importation
     * @fn on_pushButton_8_clicked
     */
    void on_pushButton_8_clicked();
    /**
     * @brief
     * affiche l'ihm Message personnalisé
     * @fn on_pushButton_clicked
     */
    void on_pushButton_clicked();
    /**
     * @brief
     * change l'état de la personne en Libre
     * @fn on_pushButton_3_clicked
     */
    void on_pushButton_3_clicked();
    /**
     * @brief
     * change l'état de la personne en Occupe
     * @fn on_pushButton_8_clicked
     */
    void on_pushButton_4_clicked();
    /**
     * @brief
     * change l'état de la personne en Absent
     * @fn on_pushButton_8_clicked
     */
    void on_pushButton_2_clicked();
    void on_pushButton_9_clicked();
};

#endif // IHMGROOM_H
