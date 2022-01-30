#include "controle.h"
#include <QDebug>


Controle::Controle(QObject *parent) : QObject(parent)
{
     qDebug() << Q_FUNC_INFO;
   //     ui = new IhmGroom;
    etatSonnette = true;

}

Controle::~Controle()
{
    qDebug() << Q_FUNC_INFO;
}

void Controle::setEtatUtilisateur(int newEtatUtilisateur)
{
    etatUtilisateur = newEtatUtilisateur;
}

int Controle::getEtatUtilisateur()
{
    return etatUtilisateur;
}

void Controle::setEtatSonnette(bool newEtatSonnette)
{
    etatSonnette = newEtatSonnette;
}

bool Controle::getEtatSonnette()
{
    return etatSonnette;
}

void Controle::setPresence(bool newPresence)
{
    presence = newPresence;
}

bool Controle::getPresence()
{
    return presence;
}


void Controle::decoderTrameGroom(QString trameGroom)
{

    setEtatUtilisateur((trameGroom.section(";",1,1)).toInt());


    if (trameGroom.section(";",2,2) == 1)
    {
        setEtatSonnette(true);
    }
    else
    {
        setEtatSonnette(false);
    }

    if (trameGroom.section(";",3,3) == 1)
    {
        setPresence(true);
    }
    else
    {
        setPresence(false);
    }

}

void Controle::setNom(QString newNom)
{
    nom = newNom;
}

QString Controle::getNom()
{
    return nom;
}
void Controle::setPrenom(QString newPrenom)
{
    prenom = newPrenom;
}
QString Controle::getPrenom()
{
    return prenom;
}
void Controle::setFonction(QString newFonction)
{
    fonction = newFonction;
}
QString Controle::getFonction()
{
    return fonction;
}

void Controle::coderTrameAffichage(QString trameAffichage)
{
    setNom(trameAffichage.section(";",1,1));

    setPrenom(trameAffichage.section(";",2,2));

    setFonction(trameAffichage.section(";",3,3));

    ui->changementNom();
    ui->changementPrenom();
    ui->changementFonction();
}
