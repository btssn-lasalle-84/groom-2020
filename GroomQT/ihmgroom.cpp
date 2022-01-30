#include "ihmgroom.h"
#include "ui_ihmgroom.h"
#include "communicationbluetooth.h"
#include "ui_communicationbluetooth.h"
#include "importation.h"
#include "ui_importation.h"
#include "messagepersonnalise.h"
#include "ui_messagepersonnalise.h"
#include "controle.h"
#include "bdd.h"
#include "ui_bdd.h"

/**
* @file ihmgroom.cpp
*
* @brief Définition de la classe IhmGroom
*
* @author Etienne Valette
*
*/

/**
 * @brief Constructeur de la classe IhmGroom
 *
 * @fn IhmGroom::IhmGroom(QWidget *parent)
 * @param parent L'objet parent Qt (0 = fenêtre principale)
 */



IhmGroom::IhmGroom(QWidget *parent) : QWidget(parent), ui(new Ui::IhmGroom)
{
    qDebug() << Q_FUNC_INFO;
    ui->setupUi(this);
    setWindowTitle(NOM_APP);

    ihmImportation = new Importation(this);
    messagePersonnalise = new MessagePersonnalise(this);
    communicationBluetooth = new CommunicationBluetooth(this);
    controle = new Controle(this);
    bdd = new Bdd(this);

    initialiserIconeSysteme();
    connect(ui->pushButton_5, SIGNAL(clicked()), this, SLOT(inversionImageSonnette()));
    /**
      * @todo Renommer les widgets
      */
    // Test :
    connect(ui->pushButton_7, SIGNAL(clicked()), this, SLOT(testerNotification()));
    connect(ui->pushButton_6, SIGNAL(clicked()), this, SLOT(afficherIhmConfiguration()));

}

/**
 * @brief Destructeur de la classe IhmGroom
 *
 * @fn IhmGroom::~IhmGroom()
 */
IhmGroom::~IhmGroom()
{
    delete ui;
     qDebug() << Q_FUNC_INFO;
}

void IhmGroom::changementNom()
{
    ui->label_Nom->setWindowTitle(controle->getNom());
}

void IhmGroom::changementPrenom()
{
    ui->label_Prenom->setWindowTitle(controle->getPrenom());
}

void IhmGroom::changementFonction()
{
    ui->label_Fonction->setWindowTitle(controle->getFonction());
}


/**
 * @brief Méthode redéfinie qui est appelée automatiquement lors d'une demande de fermeture
 *
 * @fn IhmGroom::closeEvent(QCloseEvent *event)
 * @param event L'évènement de fermeture reçu
 */
void IhmGroom::closeEvent(QCloseEvent *event)
{
    if (iconeSysteme->isVisible())
    {
        if(etatInitialIconeSysteme)
        {
            QMessageBox::information(this, "Groom", "Le programme continue à s'éxécuter. Utiliser le menu Quitter pour mettre fin à l'application Groom.");
            etatInitialIconeSysteme = false;
        }
        hide();
        event->ignore();
    }
}

/**
 * @brief Méthode qui permet à l'application de s'intaller dans la barre système
 *
 * @fn IhmGroom::initialiserIconeSysteme()
 */
void IhmGroom::initialiserIconeSysteme()
{
    // Crée les actions
    actionMinimiser = new QAction(QString::fromUtf8("Minimiser"), this);
    actionMaximiser = new QAction(QString::fromUtf8("Maximiser"), this);
    actionRestaurer = new QAction(QString::fromUtf8("Restaurer"), this);
    actionQuitter = new QAction(QString::fromUtf8("&Quitter"), this);

    // Connecte les actions
    connect(actionMinimiser, SIGNAL(triggered(bool)), this, SLOT(hide()));
    connect(actionMaximiser, SIGNAL(triggered(bool)), this, SLOT(showMaximized()));
    connect(actionRestaurer, SIGNAL(triggered(bool)), this, SLOT(showNormal()));
    connect(actionQuitter, SIGNAL(triggered(bool)), qApp, SLOT(quit()));

    // Crée le menu
    menuIconeSysteme = new QMenu(this);
    menuIconeSysteme->addAction(actionMinimiser);
    menuIconeSysteme->addAction(actionMaximiser);
    menuIconeSysteme->addAction(actionRestaurer);
    menuIconeSysteme->addSeparator();
    menuIconeSysteme->addAction(actionQuitter);

    // Crée l'icône pour la barre de tâche
    iconeSysteme = new QSystemTrayIcon(this);
    iconeSysteme->setContextMenu(menuIconeSysteme);
    iconeSysteme->setToolTip("Groom");
    QIcon icone(":/groom.png");
    iconeSysteme->setIcon(icone);
    setWindowIcon(icone);

    connect(iconeSysteme, SIGNAL(messageClicked()), this, SLOT(acquitterNotification()));
    //connect(iconeSysteme, SIGNAL(activated(QSystemTrayIcon::ActivationReason)), this, SLOT(aActiveIconeSysteme(QSystemTrayIcon::ActivationReason)));

    iconeSysteme->show();
    etatInitialIconeSysteme = true;
}

/**
 * @brief Méthode qui permet d'afficher une notification système
 *
 * @fn IhmGroom::afficherNotification(QString titre, QString message, int duree)
 * @param titre Le titre de la notification
 * @param message Le message de la notification
 * @param duree La durée en millisecondes de la notification (par défaut 5s)
 */
void IhmGroom::afficherNotification(QString titre, QString message, int duree)
{
    QIcon icone(":/groom.png");
    iconeSysteme->showMessage(titre, message, icone, duree); // duree en ms
    /*
    QSystemTrayIcon::NoIcon
    QSystemTrayIcon::Information
    QSystemTrayIcon::Warning
    QSystemTrayIcon::Critical
    */
    //QSystemTrayIcon::MessageIcon messageIcone = QSystemTrayIcon::MessageIcon(QSystemTrayIcon::Information);
    //iconeSysteme->showMessage(titre, message, messageIcone, duree);
}

/**
 * @brief Méthode qui permet d'acquitter une notification
 *
 * @fn IhmGroom::acquitterNotification()
 */
void IhmGroom::acquitterNotification()
{
    qDebug() << Q_FUNC_INFO;
}

void IhmGroom::testerNotification()
{
    afficherNotification("Groom", "Rendez-vous annulé !");
}


void IhmGroom::on_pushButton_6_clicked()
{
    communicationBluetooth->exec();
}

void IhmGroom::on_pushButton_8_clicked()
{
    ihmImportation->exec();
}

void IhmGroom::on_pushButton_clicked()
{
    messagePersonnalise->exec();
}

void IhmGroom::on_pushButton_9_clicked()
{
    bdd->exec();
}

void IhmGroom::inversionImageSonnette()
{

    if (controle->getEtatSonnette())
    {
        ui->pushButton_5->setIcon(QIcon(":/bellDisable.png"));

        controle->setEtatSonnette(false);

    }
    else
    {
        ui->pushButton_5->setIcon(QIcon(":/bellEnable.png"));
        controle->setEtatSonnette(true);
    }
}


void IhmGroom::on_pushButton_3_clicked()
{
    controle->setEtatUtilisateur(LIBRE);
}

void IhmGroom::on_pushButton_4_clicked()
{
    controle->setEtatUtilisateur(OCCUPE);
}

void IhmGroom::on_pushButton_2_clicked()
{
    controle->setEtatUtilisateur(ABSENT);
}




