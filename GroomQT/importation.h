#ifndef IMPORTATION_H
#define IMPORTATION_H

#include <QtWidgets>

/**
 * @brief
 * Classe permetant de d'importer le calendrier
 * @class Importation importation.h "importation.h"
 */

class IhmGroom;
namespace Ui {
class Importation;
}

class Importation : public QDialog
{
    Q_OBJECT

public:
    /**
     * @brief
     * Constructeur de la classe Importation
     * @fn Importation
     * @param parent
     */
    explicit Importation(QWidget *parent = nullptr);
    /**
     * @brief
     * Destructeur de la classe Importation
     * @fn ~Importation
     * @param parent
     */
    ~Importation();

private:
    Ui::Importation *ui;        /** L'interface utilisateur */
};

#endif // IMPORTATION_H
