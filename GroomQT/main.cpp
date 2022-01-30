#include "ihmgroom.h"
#include <QApplication>

/**
* @file main.cpp
*
* @brief Programme principal GroomQT
*
* @details Crée et affiche la fenêtre principale de l'application GroomQT
*
* @author Etienne Valette
*
* @fn main(int argc, char *argv[])
* @param argc
* @param argv[]
* @return int
*
*/
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    IhmGroom w;
    w.show();

    return a.exec();
}
